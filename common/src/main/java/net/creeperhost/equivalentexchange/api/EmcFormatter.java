package net.creeperhost.equivalentexchange.api;

import java.text.NumberFormat;
import java.util.Arrays;

public class EmcFormatter
{
    public static String formatEmcValue(double value)
    {
        return tidyValueShort(value);
    }

    public static NumberFormat getFormatter()
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumIntegerDigits(5);
        return format;
    }

    public static String tidyValueShort(double value)
    {
        if (value < 1000) return String.valueOf(value);

        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format("%.1f%c", value / Math.pow(1000, exp), "kMBTqQsS___".charAt(exp - 1));
    }

    public static String tidyValue(double value)
    {
        if (value < 1000) return String.valueOf(value);

        int exp = (int) (Math.log(value) / Math.log(1000));
        return String.format("%.1f%c", value / Math.pow(1000, exp), ' ') + Arrays.stream(BigNumbers.values()).toList().get(exp - 1).getName();
    }
}
