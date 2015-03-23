package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherDataRecord;

import java.util.List;

public class MaxTemperature {

    public Integer compute(final List<WeatherDataRecord> data) {

        final Integer maxTemperature = data.stream()
                .filter(WeatherDataRecord::isAirTemperaturePresent)
                .filter(WeatherDataRecord::isQualitySufficient)
                .map(WeatherDataRecord::getAirTemperature)
                .reduce(Math::max).get();

        return maxTemperature;
    }
}
