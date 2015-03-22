package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherDataImporter;
import mapreducedata.maxtemperature.WeatherDataRecord;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MaxTemperatureTest {

    @Test
    public void max_temperature_from_single_year_should_be_correctly_computed() {

        // given
        final List<WeatherDataRecord> records = new WeatherDataImporter().importData("max-temperature/input/1901");

        // when
        final Integer maxTemperature = records.stream()
                .filter(record -> record.getAirTemperature() != 9999)
                .filter(record -> record.getQuality().matches("[01459]"))
                .map(WeatherDataRecord::getAirTemperature)
                .reduce(Math::max).get();

        // then
        assertThat(maxTemperature, is(equalTo(317)));
    }
}
