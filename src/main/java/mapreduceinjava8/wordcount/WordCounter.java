package mapreduceinjava8.wordcount;

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
}
