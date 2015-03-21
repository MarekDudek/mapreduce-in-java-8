package mapreduceinjava8.utils;

import com.google.common.collect.Sets;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

public class HasEqualContents<KEY, VALUE> extends TypeSafeMatcher<Map<KEY, VALUE>> {

    private final Map<KEY, VALUE> expected;

    private Object difference;

    Sets.SetView<Map.Entry<KEY, VALUE>> missingInExpected;
    Sets.SetView<Map.Entry<KEY, VALUE>> obsoleteInActual;

    public static <KEY, VALUE> HasEqualContents<KEY, VALUE> hasEqualContents(final Map<KEY, VALUE> expected) {
        return new HasEqualContents<>(expected);
    }

    HasEqualContents(final Map<KEY, VALUE> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final Map<KEY, VALUE> actual) {

        missingInExpected = Sets.difference(expected.entrySet(), actual.entrySet());
        obsoleteInActual = Sets.difference(actual.entrySet(), expected.entrySet());

        if (missingInExpected.isEmpty() && obsoleteInActual.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void describeTo(final Description description) {
        description.appendValue(expected);
    }


    @Override
    protected void describeMismatchSafely(final Map<KEY, VALUE> item, final Description description) {

        super.describeMismatchSafely(item, description);

        if (!missingInExpected.isEmpty()) {
            newLine(description);
            description.appendText("Elements missing from expected: ");
            description.appendValue(missingInExpected);
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
