package net.creeperhost.equivalentexchange.api;

public enum BigNumbers
{
    THOUSAND("Thousand"),
    MILLION("Million"),
    BILLION("Billion"),
    TRILLION("Trillion"),
    QUADRILLION("Quadrillion"),
    QUINTILLION("Quintillion"),
    SEXTILLION("Sextillion"),
    SEPTILLION("Septillion"),
    OCTILLION("Octillion"),
    NONILLION("Nonillion"),
    DECILLION("Decillion"),
    SEPTENDECILLION("Septendecillion"),
    OCTODECILLION("Octodecillion"),
    NOVEMDECILLION("Novemdecillion"),
    VIGINTILLION("Vigintillion"),
    UNVIGINTILLION("Unvigintillion"),
    DUOVIGINTILLION("Duovigintillion"),
    TREVIGINTILLION("Trevigintillion"),
    QUATTUORVIGINTILLION("Quattuorvigintillion"),
    QUINVIGINTILLION("Quinvigintillion"),
    SEXVIGINTILLION("Sexvigintillion"),
    DUOTRIGINTILLION("Duotrigintillion"),
    TRESTRIGINTILLION("Trestrigintillion"),
    QUATTUORTRIGINTILLION("Quattuortrigintillion"),
    QUINTRIGINTILLION("Quintrigintillion"),
    SEXTRIGINTILLION("Sextrigintillion"),
    SEPTRIGINTILLION("Septrigintillion"),
    OCTRIGINTILLION("Octrigintillion"),
    NOVEMTRIGINTILLION("Novemtrigintillion"),
    QUADRAGINTILLION("Quadragintillion");

    final String name;

    BigNumbers(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
