package mapreduceinjava8.wordcount;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import mapreduceinjava8.utils.HasEqualContents;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static mapreduceinjava8.utils.HasEqualContents.hasEqualContents;
import static org.junit.Assert.assertThat;

public class HasEqualContentsTest {

    @Test
    public void different_implementations_should_be_equal() {

        // given
        final Map<String, Integer> one = new ImmutableMap.Builder()
                .put("one", 1).build();

        final Map<String, Integer> two = Maps.newHashMap();
        two.put("one", 1);

        // then
        assertThat(one, hasEqualContents(two));
    }

    @Test
    public void keys_must_be_equal() {

    }
}
