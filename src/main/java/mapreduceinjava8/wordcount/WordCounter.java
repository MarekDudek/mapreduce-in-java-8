package mapreduceinjava8.wordcount;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordCounter {

    private static final Pattern INTO_WORDS = Pattern.compile("\\W+");

    public Map<String, Long> wordCount(final String line) {

        final String[] words = INTO_WORDS.split(line);
        final Stream<String> stream = Stream.of(words);

        final Map<String, Long> wordCount = stream.collect(
                groupingBy(identity(), counting())
        );

        return wordCount;
    }
}
