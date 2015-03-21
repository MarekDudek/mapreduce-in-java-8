package mapreduceinjava8.wordcount;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class WordCountTest {

    // given
    private static final String GETTYSBURG_ADDRESS = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, " +
            "and dedicated to the proposition that all men are created equal. Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, " +
            "can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here " +
            "gave their lives that that nation might live. It is altogether fitting and proper that we should do this. But, in a larger sense, we can not dedicate -- we can not consecrate -- " +
            "we can not hallow -- this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, " +
            "nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work " +
            "which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us -- that from these honored dead " +
            "we take increased devotion to that cause for which they gave the last full measure of devotion -- that we here highly resolve that these dead shall not have died in vain -- " +
            "that this nation, under God, shall have a new birth of freedom -- and that government of the people, by the people, for the people, shall not perish from the earth.";

    private static final int GETTYSBURG_ADDRESS_UNIQUE_WORDS = 154;
    private static final long GETTYSBURG_ADDRESS_TOTAL_WORDS = 272L;

    private static final String THOMAS_PAINE_QUOTE = "I love the man that can smile in trouble, that can gather strength from distress, and grow brave by reflection." +
            "'Tis the business of little minds to shrink, but he whose heart is firm, and whose conscience approves his conduct, will pursue his principles unto death.";


    /** System under test. */
    private WordCounter wordCounter;

    private TextSplitter splitter;

    @Before
    public void setUp() {

        wordCounter = new WordCounter();

        splitter = new TextSplitter();
    }

    @Test
    public void list_of_words_should_be_word_counted() {

        // given
        final String[] array = splitter.splitIntoArray(GETTYSBURG_ADDRESS);
        final List<String> words = Arrays.asList(array);

        // when
        final Map<String, Long> wordCount = words.stream()
                .collect(
                        groupingBy(identity(), counting())
                );

        // then
        assertThat(wordCount.keySet(), hasSize(GETTYSBURG_ADDRESS_UNIQUE_WORDS));
    }

    @Test
    public void string_should_be_split() {

        // when
        final Stream<String> split = Stream.of(GETTYSBURG_ADDRESS.split("\\W+"));

        // then
        final long count = split.count();
        assertThat(count, is(equalTo(GETTYSBURG_ADDRESS_TOTAL_WORDS)));
    }

    @Test
    public void word_count_should_work_on_single_line() {

        // when
        final Map<String, Long> wordCount = wordCounter.wordCount(GETTYSBURG_ADDRESS);

        // then
        assertThat(wordCount.keySet(), hasSize(GETTYSBURG_ADDRESS_UNIQUE_WORDS));
    }

    @Test
    public void multiple_word_counts_should_be_combined() {

        // given
        final Map<String, Long> gettysburgAddress = wordCounter.wordCount(GETTYSBURG_ADDRESS);
        final Map<String, Long> thomasPaineQuote = wordCounter.wordCount(THOMAS_PAINE_QUOTE);

        // when
        final Stream<Map<String, Long>> wordCounts = Stream.of(gettysburgAddress, thomasPaineQuote);

        final Map<String, Long> merged = wordCounts
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a + b)
                );

        // then
        final Map<String, Long> comparison = wordCounter.wordCount(GETTYSBURG_ADDRESS + " " + THOMAS_PAINE_QUOTE);

        for (final Map.Entry<String, Long> entry : merged.entrySet()) {

            final String word = entry.getKey();
            final Long count = entry.getValue();

            assertThat(count, is(equalTo(comparison.get(word))));
        }
    }
}
