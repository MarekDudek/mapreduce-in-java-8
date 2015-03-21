package mapreduceinjava8.utils;

import com.google.common.collect.Sets;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

public class HasEqualContents<KEY, VALUE> extends TypeSafeMatcher<Map<KEY, VALUE>> {

    private final Map<KEY, VALUE> expected;

    private Sets.SetView<Map.Entry<KEY, VALUE>> missingFromExpected;
    private Sets.SetView<Map.Entry<KEY, VALUE>> obsoleteInActual;

    public static <KEY, VALUE> HasEqualContents<KEY, VALUE> hasEqualContents(final Map<KEY, VALUE> expected) {
        return new HasEqualContents<>(expected);
    }

    HasEqualContents(final Map<KEY, VALUE> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final Map<KEY, VALUE> actual) {

        missingFromExpected = Sets.difference(expected.entrySet(), actual.entrySet());
        obsoleteInActual = Sets.difference(actual.entrySet(), expected.entrySet());

        return missingFromExpected.isEmpty() && obsoleteInActual.isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(expected);
    }


    @Override
    protected void describeMismatchSafely(final Map<KEY, VALUE> item, final Description description) {

        super.describeMismatchSafely(item, description);

        if (!missingFromExpected.isEmpty()) {
            newLine(description);
            description.appendText("Elements missing from expected: ");
            description.appendValue(missingFromExpected);
        }

        if (!obsoleteInActual.isEmpty()) {
            newLine(description);
            description.appendText("Elements obsolete in actual: ");
            description.appendValue(obsoleteInActual);
        }
    }

    private void newLine(final Description description) {
        description.appendText(String.format("%n"));
    }
}
