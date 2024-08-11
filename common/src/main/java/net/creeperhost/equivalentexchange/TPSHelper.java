package net.creeperhost.equivalentexchange;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;

public class TPSHelper
{
    //TODO this value should be cached and updated after x amount of time
    public static double getMeanTPS(ServerLevel serverLevel)
    {
        long[] times = serverLevel.getServer().getTickTimesNanos();
        double meanTickTime = mean(times) * 1.0E-6D;
        double meanTPS = TimeUtil.MILLISECONDS_PER_SECOND / Math.max(meanTickTime, serverLevel.getServer().tickRateManager().millisecondsPerTick());
        return meanTPS;
    }

    private static long mean(long[] values)
    {
        long sum = 0L;
        for (long v : values)
            sum += v;
        return sum / values.length;
    }
}
