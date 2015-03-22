package mapreduceinjava8.wordcount;

import com.google.common.collect.Maps;
import mapreducedata.FileAccessor;

import java.util.List;
import java.util.Map;

public class WordCountHadoopResultsImporter {

    private static final String TABULATOR = "\t";

    public Map<String, Long> importResults(final String file) {

        final List<String> lines = FileAccessor.readLinesFromResources(file);

        final Map<String, Long> results = Maps.newHashMap();

        for (final String line : lines) {

            final String[] fields = line.split(TABULATOR);

            final String word = fields[0];
            final Long count = Long.parseLong(fields[1]);

            results.put(word, count);
        }

        return results;
    }
}
