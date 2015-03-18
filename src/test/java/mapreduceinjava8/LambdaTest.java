package mapreduceinjava8;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class LambdaTest {

    @Test
    public void test() {

        // given
        final List<Integer> numbers = asList(1, 2, 3, 4, 5);

        // when
        final List<Integer> even = numbers
                .stream()
                .filter(x -> x % 2 == 0)
                .collect(toList());

        // then
        assertThat(even, hasSize(2));
    }
}
