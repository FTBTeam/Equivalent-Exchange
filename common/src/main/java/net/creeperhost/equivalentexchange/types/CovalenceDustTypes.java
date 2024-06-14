package net.creeperhost.equivalentexchange.types;

public enum CovalenceDustTypes
{
    LOW("covalence_dust_low"),
    MEDIUM("covalence_dust_medium"),
    HIGH("covalence_dust_high");

    final String name;

    CovalenceDustTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
