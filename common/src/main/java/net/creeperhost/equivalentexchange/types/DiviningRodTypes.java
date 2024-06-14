package net.creeperhost.equivalentexchange.types;

public enum DiviningRodTypes
{
    LOW_DIVINING_ROD("low_diving_rod", 16),
    MEDIUM_DIVINING_ROD("medium_diving_rod", 32),
    HIGH_DIVINING_ROD("high_diving_rod", 128);

    final String name;
    final int range;

    DiviningRodTypes(String name, int range)
    {
        this.name = name;
        this.range = range;
    }

    public String getName()
    {
        return name;
    }

    public int getRange()
    {
        return range;
    }
}
