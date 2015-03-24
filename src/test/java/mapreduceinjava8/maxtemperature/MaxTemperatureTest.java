package mapreduceinjava8.maxtemperature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mapreducedata.maxtemperature.WeatherData;
import mapreducedata.maxtemperature.WeatherDataImporter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MaxTemperatureTest {

    // given
    private static final List<WeatherData> DATA_1901 = new WeatherDataImporter().importData("max-temperature/input/1901");
    private static final List<WeatherData> DATA_1902 = new WeatherDataImporter().importData("max-temperature/input/1902");

    // expected results
    public static final ImmutableList<Integer> MAX_TEMPERATURES = ImmutableList.of(317, 244);
    public static final ImmutableMap<Integer, Integer> MAX_TEMPERATURE_FOR_YEARS = ImmutableMap.of(1901, 317, 1902, 244);

    /** System under test. */
    private MaxTemperature maxTemperature;

    @Before
    public void setUp() {
        maxTemperature = new MaxTemperature();
    }

    @Test
    public void max_temperature_from_single_collection() {

        // when
        final Integer temperature = maxTemperature.inSingleCollection(DATA_1902);

        // then
        assertThat(temperature, is(equalTo(244)));
    }

    @Test
    public void maybe_max_temperature_from_single_collection() {

        // when
        final Optional<Integer> temperature = maxTemperature.maybeInSingleCollection(DATA_1902);

        // then
        assertThat(temperature, is(equalTo(Optional.of(244))));
    }

    @Test
    public void max_temperature_from_multiple_collections() {

        // when
        final List<Integer> temperatures = maxTemperature.inMultipleCollections(DATA_1901, DATA_1902);

        // then
        assertThat(MAX_TEMPERATURES, is(temperatures));
    }

    @Test
    public void max_temperature_from_multiple_collections_for_years() {

        // given
        final ImmutableMap<Integer, List<WeatherData>> recordsForYears = ImmutableMap.of(1901, DATA_1901, 1902, DATA_1902);

        // when
        final Map<Integer, Integer> temperaturesForYears = maxTemperature.inMultipleCollectionsForYears(recordsForYears);

        // then
        assertThat(MAX_TEMPERATURE_FOR_YEARS, hasEqualContents(temperaturesForYears));
    }
}
