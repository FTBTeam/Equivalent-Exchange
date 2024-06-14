package net.creeperhost.equivalentexchange.types;

public enum CollectorType
{
    BASIC("basic_collector", 4D, 10000),
    DARK("dark_matter_collector", 12D, 30000),
    RED("red_matter_collector", 40D, 60000);

    final String name;
    final double generation;
    final double maxStorage;

    CollectorType(String name, double generation, double maxStorage)
    {
        this.name = name;
        this.generation = generation;
        this.maxStorage = maxStorage;
    }

    public double getGeneration()
    {
        return generation;
    }

    public String getName()
    {
        return name;
    }

    public double getMaxStorage()
    {
        return maxStorage;
    }
}
