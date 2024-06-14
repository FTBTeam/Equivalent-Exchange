package net.creeperhost.equivalentexchange.types;

public enum KleinStarTypes
{
    EIN("ein", 50000),
    ZWEI("zwei", 200000),
    DREI("drei", 800000),
    VIER("vier", 3200000),
    SPHERE("sphere", 12800000),
    OMEGA("omega", 51200000);

    public final String name;
    public final double maxStorage;

    KleinStarTypes(String name, double maxStorage)
    {
        this.name = name;
        this.maxStorage = maxStorage;
    }
}
