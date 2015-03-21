package mapreduceinjava8.utils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

public class HasEqualContents<KEY, VALUE> extends TypeSafeMatcher<Map<? extends KEY, ? extends VALUE>> {

    private final Map<KEY, VALUE> expected;

    public static <KEY, VALUE> HasEqualContents<KEY, VALUE> hasEqualContents(final Map<KEY, VALUE> expected) {
        return new HasEqualContents<>(expected);
    }

    private HasEqualContents(final Map<KEY, VALUE> expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(final Map<? extends KEY, ? extends VALUE> actual) {
        return expected.entrySet().equals(actual.entrySet());
    }

    @Override
    public void describeTo(final Description description) {

    }
}
