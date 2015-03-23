package mapreduceinjava8.maxtemperature;

import com.google.common.collect.ImmutableList;
import mapreducedata.maxtemperature.WeatherDataImporter;
import mapreducedata.maxtemperature.WeatherData;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.ImmutableList.of;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MaxTemperatureTest {

    // given

    private static final List<WeatherData> DATA_1901 = new WeatherDataImporter().importData("max-temperature/input/1901");
    private static final List<WeatherData> DATA_1902 = new WeatherDataImporter().importData("max-temperature/input/1902");

    @Test
    public void max_temperature_from_single_year_should_be_correctly_computed() {

        // when
        final Integer maxTemperature = new MaxTemperature().maxTemperatureInSingleCollection(DATA_1902);

        // then
        assertThat(maxTemperature, is(equalTo(244)));
    }

    @Test
    public void max_temperature_from_multiple_years_should_be_correctly_computed() {

        // when
        final List<Integer> maxTemperatures = Stream.of(DATA_1901, DATA_1902)
                .map(list -> new MaxTemperature().maxTemperatureInSingleCollection(list))
                .collect(Collectors.toList());

        // then
        assertThat(of(317, 244), is(maxTemperatures));
    }

    @Test
    public void maybe_max_temperature_from_multiple_collections() {

        // when
        final List<Integer> maxTemperatures = Stream.of(DATA_1901, DATA_1902)
                .map(list -> new MaxTemperature().maybeMaxTemperatureInSingleCollection(list))
                .map(maybe -> maybe.get())
                .collect(Collectors.toList());

        // then
        assertThat(of(317, 244), is(maxTemperatures));
    }
}
