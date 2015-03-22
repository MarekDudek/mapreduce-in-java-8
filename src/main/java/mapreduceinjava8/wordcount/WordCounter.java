package mapreduceinjava8.wordcount;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordCounter {

    public Map<String, Long> wordCount(final String line) {

        final String[] words = new TextSplitter().splitIntoArray(line);
        final Stream<String> stream = Stream.of(words);

        final Map<String, Long> wordCount = stream.collect(
                Collectors.groupingBy(Function.identity(), Collectors.counting())
        );

        return wordCount;
    }

    public Map<String, Long> wordCount(final List<List<String>> lines) {

        final Stream<Map<String, Long>> wordCounts = lines.stream()
                .flatMap(Collection::stream).map(string -> wordCount(string));

        final Map<String, Long> wordCount = wordCounts
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a + b)
                );

        return wordCount;
    }
}
