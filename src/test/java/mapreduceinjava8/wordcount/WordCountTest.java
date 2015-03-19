package mapreduceinjava8.wordcount;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static mapreduceinjava8.utils.NumberUtils.zeroIfNull;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class WordCountTest {

    // given
    private static final String INTO_WORDS = "\\W+";

    private static final String GETTYSBURG_ADDRESS = "Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, " +
            "and dedicated to the proposition that all men are created equal. Now we are engaged in a great civil war, testing whether that nation, or any nation so conceived and so dedicated, " +
            "can long endure. We are met on a great battle-field of that war. We have come to dedicate a portion of that field, as a final resting place for those who here " +
            "gave their lives that that nation might live. It is altogether fitting and proper that we should do this. But, in a larger sense, we can not dedicate -- we can not consecrate -- " +
            "we can not hallow -- this ground. The brave men, living and dead, who struggled here, have consecrated it, far above our poor power to add or detract. The world will little note, " +
            "nor long remember what we say here, but it can never forget what they did here. It is for us the living, rather, to be dedicated here to the unfinished work " +
            "which they who fought here have thus far so nobly advanced. It is rather for us to be here dedicated to the great task remaining before us -- that from these honored dead " +
            "we take increased devotion to that cause for which they gave the last full measure of devotion -- that we here highly resolve that these dead shall not have died in vain -- " +
            "that this nation, under God, shall have a new birth of freedom -- and that government of the people, by the people, for the people, shall not perish from the earth.";

    private static final int GETTYSBURG_ADDRESS_UNIQUE_WORDS = 142;
    private static final long GETTYSBURG_ADDRESS_TOTAL_WORDS = 272L;

    private static final String THOMAS_PAINE_QUOTE = "I love the man that can smile in trouble, that can gather strength from distress, and grow brave by reflection." +
            "'Tis the business of little minds to shrink, but he whose heart is firm, and whose conscience approves his conduct, will pursue his principles unto death.";


    /** System under test. */
    private WordCounter wordCounter;

    @Before
    public void setUp() {

        wordCounter = new WordCounter();
    }

    @Test
    public void list_of_words_should_be_word_counted() {

        // given
        final String[] array = GETTYSBURG_ADDRESS.split(INTO_WORDS);
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
        final Stream<Map.Entry<String, Long>> entryStream = wordCounts.flatMap(map -> map.entrySet().stream());


        final Function<Map.Entry<String, Long>, String> keyMapper = entry -> entry.getKey();
        final Function<Map.Entry<String, Long>, Long> valueMapper = entry -> entry.getValue();

        final BinaryOperator<Long> mergeFunction = new BinaryOperator<Long>() {
            @Override
            public Long apply(final Long first, final Long second) {
                return new Long(first.longValue() + second.longValue());
            }
        };

        final Map<String, Long> merged = entryStream.collect(
                Collectors.toMap(keyMapper, valueMapper, mergeFunction)
        );

        // then

        for (final Map.Entry<String, Long> entry : merged.entrySet()) {

            final String word = entry.getKey();
            final Long count = entry.getValue();

            final Long component1 = zeroIfNull(gettysburgAddress.get(word));
            final Long component2 = zeroIfNull(thomasPaineQuote.get(word));

            assertThat(count, is(equalTo(component1 + component2)));
        }
    }
}
