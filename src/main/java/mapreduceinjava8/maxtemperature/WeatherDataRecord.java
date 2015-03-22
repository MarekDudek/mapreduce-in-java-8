package mapreduceinjava8.maxtemperature;

public class WeatherDataRecord {
    private final String year;
    private final int airTemperature;
    private final String quality;

    public WeatherDataRecord(final String year, final int airTemperature, final String quality) {
        this.year = year;
        this.airTemperature = airTemperature;
        this.quality = quality;
    }
}
