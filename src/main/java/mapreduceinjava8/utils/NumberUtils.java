package mapreduceinjava8.utils;

public class NumberUtils {

    public static final long ZERO = 0L;

    public static Long zeroIfNull(final Long number) {
        if (number == null) {
            return ZERO;
        }
        return number;
    }
}
