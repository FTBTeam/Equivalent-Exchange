package net.creeperhost.equivalentexchange.types;

public enum RelayTypes
{
    MK1("basic_relay"),
    MK2("dark_matter_relay"),
    MK3("red_matter_relay");

    final String name;

    RelayTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
