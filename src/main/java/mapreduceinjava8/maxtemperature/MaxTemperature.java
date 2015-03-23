package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherData;

import java.util.List;

public class MaxTemperature {

    public Integer compute(final List<WeatherData> data) {

        final Integer maxTemperature = data.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature)
                .reduce(Math::max).get();

        return maxTemperature;
    }
}
