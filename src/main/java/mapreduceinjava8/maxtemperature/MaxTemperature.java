package mapreduceinjava8.maxtemperature;

import mapreducedata.maxtemperature.WeatherData;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaxTemperature {

    public Integer inSingleCollection(final Collection<WeatherData> collection) {

        final Optional<Integer> maybe = maybeInSingleCollection(collection);

        return maybe.get();
    }

    public Optional<Integer> maybeInSingleCollection(final Collection<WeatherData> collections) {

        final Stream<Integer> temperatures = collections.stream()
                .filter(WeatherData::isAirTemperaturePresent)
                .filter(WeatherData::isQualitySufficient)
                .map(WeatherData::getAirTemperature);

        return temperatures.reduce(Math::max);
    }

    public List<Integer> inMultipleCollections(final List<WeatherData>... collections) {

        final List<Integer> maxTemperatures = Stream.of(collections)
                .map(collection -> inSingleCollection(collection))
                .collect(Collectors.toList());

        return maxTemperatures;
    }

    public Map<Integer, Integer> inMultipleCollectionsForYears(final Map<Integer, List<WeatherData>> recordsForYears) {

        final Map<Integer, Integer> maxTemperatureForYears = recordsForYears
                .entrySet().stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                recordsForYear -> inSingleCollection(recordsForYear.getValue())
                        )
                );

        return maxTemperatureForYears;
    }
}
