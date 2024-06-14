package net.creeperhost.equivalentexchange.types;

public enum MatterTypes
{
    DARK("dark"),
    RED("red");

    final String name;

    MatterTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
