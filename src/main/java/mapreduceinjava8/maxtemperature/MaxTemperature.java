package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherData;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class MaxTemperature {

    public Integer maxTemperatureInSingleCollection(final Collection<WeatherData> data) {

        final Integer maxTemperature = data.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature)
                .reduce(Math::max).get();

        return maxTemperature;
    }

    public Optional<Integer> maybeMaxTemperatureInSingleCollection(final Collection<WeatherData> data) {

        final Stream<Integer> integers = data.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature);

        return integers.reduce(Math::max);
    }
}
