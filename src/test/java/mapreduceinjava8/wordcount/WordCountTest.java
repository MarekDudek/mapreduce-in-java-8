package mapreduceinjava8.wordcount;

import com.google.common.base.Joiner;
import mapreducedata.FileAccessor;
import mapreducedata.wordcount.WordCountHadoopResultsImporter;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class WordCountTest {

    // given

    private static final List<String> GETTYSBURG_ADDRESS = FileAccessor.readLinesFromResources("wordcount/input/gettysburg-address.txt");
    private static final List<String> THOMAS_PAINE_QUOTE = FileAccessor.readLinesFromResources("wordcount/input/thomas-paine-quote-1.txt");

    private static final List<List<String>> ALL_LINES = Arrays.asList(GETTYSBURG_ADDRESS, THOMAS_PAINE_QUOTE);

    private static final Map<String, Long> HADOOP_RESULTS = new WordCountHadoopResultsImporter().importResults("wordcount/output/part-00000");

    private static final int GETTYSBURG_ADDRESS_UNIQUE_WORDS = 154;

    /** System under test. */
    private WordCounter wordCounter;

    @Before
    public void setUp() {

        wordCounter = new WordCounter();
    }

    @Test
    public void word_count_should_work_on_single_line() {

        // given
        final String text = Joiner.on(' ').join(GETTYSBURG_ADDRESS);

        // when
        final Map<String, Long> wordCount = wordCounter.wordCount(text);

        // then
        assertThat(wordCount.keySet(), hasSize(GETTYSBURG_ADDRESS_UNIQUE_WORDS));
    }

    @Test
    public void input_from_files_should_be_word_counted() {

        // when
        final Map<String, Long> wordCount = wordCounter.wordCount(ALL_LINES);

        // then
        assertThat(wordCount, hasEqualContents(HADOOP_RESULTS));
    }
}
