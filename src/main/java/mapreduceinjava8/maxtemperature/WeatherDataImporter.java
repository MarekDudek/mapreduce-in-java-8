package mapreduceinjava8.maxtemperature;

import com.google.common.collect.Lists;
import mapreducedata.FileAccessor;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataImporter {

    public List<WeatherDataRecord> importData(final String file) {

        final List<WeatherDataRecord> records = Lists.newArrayList();

        final List<String> lines = FileAccessor.readLinesFromResources(file);
        for (final String line: lines) {
            final WeatherDataRecord record = new WeatherDataRecord(null, 0, null);
            records.add(record);
        }

        return records;
    }
}
