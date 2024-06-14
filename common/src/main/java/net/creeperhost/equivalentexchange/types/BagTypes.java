package net.creeperhost.equivalentexchange.types;

public enum BagTypes
{
    BLACK("black"),
    BLUE("blue"),
    BROWN("brown"),
    CYAN("cyan"),
    GRAY("gray"),
    GREEN("green"),
    LIGHT_BLUE("light_blue"),
    LIGHT_GRAY("light_gray"),
    LIME("lime"),
    MAGENTA("magenta"),
    ORANGE("orange"),
    PINK("pink"),
    PURPLE("purple"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    final String name;

    BagTypes(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
