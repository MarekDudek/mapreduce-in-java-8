package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherData;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaxTemperature {

    public Integer inSingleCollection(final Collection<WeatherData> collection) {

        final Integer maxTemperature = collection.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature)
                .reduce(Math::max).get();

        return maxTemperature;
    }

    public Optional<Integer> maybeInSingleCollection(final Collection<WeatherData> collections) {

        final Stream<Integer> integers = collections.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature);

        return integers.reduce(Math::max);
    }

    public List<Integer> inMultipleCollections(final List<WeatherData> ... collections) {

        final List<Integer> maxTemperatures = Stream.of(collections)
                .map(collection -> maybeInSingleCollection(collection))
                .map(maybe -> maybe.get())
                .collect(Collectors.toList());

        return maxTemperatures;
    }
}
