package mapreduceinjava8.maxtemperature;

import com.google.common.collect.ImmutableMap;
import mapreducedata.maxtemperature.WeatherData;
import mapreducedata.maxtemperature.WeatherDataImporter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.of;
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
        assertThat(of(317, 244), is(maxTemperatures));
    }

    @Test
    public void maybe_max_temperature_from_multiple_collections() {

        // when
        final List<Integer> maxTemperatures = Stream.of(DATA_1901, DATA_1902)
                .map(MAX_TEMPERATURE::maybeInSingleCollection)
                .map(maybe -> maybe.get())
                .collect(Collectors.toList());

        // then
        assertThat(of(317, 244), is(maxTemperatures));
    }

    @Test
    public void test() {

        // given
        final ImmutableMap<Integer, List<WeatherData>> data = ImmutableMap.of(1901, DATA_1901, 1902, DATA_1902);

        // when
        final Stream<Map.Entry<Integer, List<WeatherData>>> entries = data.entrySet().stream();
        final Map<Integer, Integer> result = entries.collect(Collectors.toMap(Map.Entry::getKey, entry -> MAX_TEMPERATURE.inSingleCollection(entry.getValue())));

        // then
        assertThat(ImmutableMap.of(1901, 317, 1902, 244), hasEqualContents(result));

    }
}
