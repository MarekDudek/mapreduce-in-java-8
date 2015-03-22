package mapreduceinjava8.maxtemperature;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MaxTemperatureTest {

    @Test
    public void importing_data() {

        // when
        final List<WeatherDataRecord> records = new WeatherDataImporter().importData("max-temperature/input/1901");

        // then
        assertThat(records, hasSize(6565));
    }

}
