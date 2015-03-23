package mapreduceinjava8.wordcount;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.junit.Assert.assertThat;

public class HasEqualContentsTest {

    @Test
    public void different_implementations_should_be_equal() {

        // given
        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("one", 1);

        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("one", 1).build();

        // then
        assertThat(actual, hasEqualContents(expected));
    }

    @Test(expected = AssertionError.class)
    public void keys_must_be_equal() {

        // given
        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("one", 1);

        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("two", 1).build();

        // then
        assertThat(actual, hasEqualContents(expected));
    }

    @Test(expected = AssertionError.class)
    public void values_must_be_equal() {

        // given
        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("one", 1);

        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("one", 2).build();

        // then
        assertThat(actual, hasEqualContents(expected));
    }

    @Test(expected = AssertionError.class)
    public void no_entry_can_be_missing_from_expected() {

        // given
        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("one", 1);
        expected.put("two", 2);

        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("one", 1).build();

        // then
        assertThat(actual, hasEqualContents(expected));
    }

    @Test(expected = AssertionError.class)
    public void no_entry_can_be_obsolete_in_actual() {

        // given
        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("one", 1);

        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("one", 1)
                .put("two", 2)
                .build();

        // then
        assertThat(actual, hasEqualContents(expected));
    }

}
