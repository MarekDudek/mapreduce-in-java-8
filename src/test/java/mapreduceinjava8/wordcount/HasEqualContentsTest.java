package mapreduceinjava8.wordcount;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mapreduceinjava8.utils.HasEqualContents;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HasEqualContentsTest {

    @Test
    public void different_implementations_should_be_equal() {

        // given
        final Map<String, Integer> one = ImmutableMap.<String, Integer>builder()
                .put("one", 1).build();

        final Map<String, Integer> two = Maps.newHashMap();
        two.put("one", 1);

        // then
        assertThat(one, hasEqualContents(two));
    }

    @Test(expected = AssertionError.class)
    public void keys_must_be_equal() {

        // given
        final Map<String, Integer> actual = ImmutableMap.<String, Integer>builder()
                .put("one", 1).build();

        final Map<String, Integer> expected = Maps.newHashMap();
        expected.put("two", 1);

        // then
        assertThat(actual, hasEqualContents(expected));
    }
}
