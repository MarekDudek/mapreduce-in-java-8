package mapreduceinjava8;

import com.google.common.base.Charsets;
import mapreducedata.FileAccessor;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LambdaTest {

    public static final String EXAMPLE_FILE = "us/input/counties.csv";

    @Test
    public void lambdas_should_work() {

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

    @Test
    public void data_from_other_project_should_be_accessible() {

        // given
        final List<String> lines = FileAccessor.readLinesFromResources(EXAMPLE_FILE);

        // then
        assertThat(lines, hasSize(3142));
    }

    @Test
    public void test() throws IOException {

        // when
        final InputStream stream = getClass().getClassLoader().getResourceAsStream(EXAMPLE_FILE);
        final List<String> lines = IOUtils.readLines(stream);

        // then
        assertThat(lines, hasSize(3142));
    }
}
