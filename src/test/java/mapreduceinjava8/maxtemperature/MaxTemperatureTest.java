package mapreduceinjava8.maxtemperature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mapreducedata.maxtemperature.WeatherData;
import mapreducedata.maxtemperature.WeatherDataImporter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MaxTemperatureTest {

    // given

    private static final List<WeatherData> DATA_1901 = new WeatherDataImporter().importData("max-temperature/input/1901");
    private static final List<WeatherData> DATA_1902 = new WeatherDataImporter().importData("max-temperature/input/1902");

    /** System under test. */
    public static final MaxTemperature MAX_TEMPERATURE = new MaxTemperature();

    // expected results
    public static final ImmutableList<Integer> MAX_TEMPERATURES = ImmutableList.of(317, 244);
    public static final ImmutableMap<Integer, Integer> MAX_TEMPERATURE_FOR_YEARS = ImmutableMap.of(1901, 317, 1902, 244);

    @Test
    public void max_temperature_from_single_year_should_be_correctly_computed() {

        // when
        final Integer maxTemperature = new MaxTemperature().inSingleCollection(DATA_1902);

        // then
        assertThat(maxTemperature, is(equalTo(244)));
    }

    @Test
    public void max_temperature_from_multiple_years_should_be_correctly_computed() {

        // when
        final List<Integer> maxTemperatures = Stream.of(DATA_1901, DATA_1902)
                .map(MAX_TEMPERATURE::inSingleCollection)
                .collect(Collectors.toList());

        // then
        assertThat(MAX_TEMPERATURES, is(maxTemperatures));
    }

    @Test
    public void maybe_max_temperature_from_multiple_collections() {

        // when
        final List<Integer> maxTemperatures = Stream.of(DATA_1901, DATA_1902)
                .map(MAX_TEMPERATURE::maybeInSingleCollection)
                .map(maybe -> maybe.get())
                .collect(Collectors.toList());

        // then
        assertThat(MAX_TEMPERATURES, is(maxTemperatures));
    }

    @Test
    public void test() {

        // given
        final ImmutableMap<Integer, List<WeatherData>> recordsForYears = ImmutableMap.of(1901, DATA_1901, 1902, DATA_1902);

        // when
        final Map<Integer, Integer> maxTemperatureForYears = recordsForYears.entrySet().stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, recordsForYear -> MAX_TEMPERATURE.inSingleCollection(recordsForYear.getValue()))
                );

        // then
        assertThat(MAX_TEMPERATURE_FOR_YEARS, hasEqualContents(maxTemperatureForYears));
    }
}
