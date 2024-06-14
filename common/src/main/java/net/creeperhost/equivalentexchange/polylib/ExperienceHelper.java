package net.creeperhost.equivalentexchange.polylib;

import net.minecraft.world.entity.player.Player;

public class ExperienceHelper
{
    public static int getPlayersXP(Player player)
    {
        return (int) (getXPForLvl(player.experienceLevel) + player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static void removeXP(Player player, int amount)
    {
        int totalExperience = getPlayersXP(player) - amount;
        if (totalExperience < 0)
        {
            player.totalExperience = 0;
            player.experienceLevel = 0;
            player.experienceProgress = 0;
        }
        else
        {
            player.totalExperience = totalExperience;
            player.experienceLevel = getLvlForXP(totalExperience);
            player.experienceProgress = (float) (totalExperience - getXPForLvl(player.experienceLevel)) / (float) player.getXpNeededForNextLevel();
        }
    }

    public static void addXP(Player player, int amount)
    {
        int exp = getPlayersXP(player) + amount;
        player.totalExperience = exp;
        player.experienceLevel = getLvlForXP(exp);
        player.experienceProgress = (float) (exp - getXPForLvl(player.experienceLevel)) / (float) player.getXpNeededForNextLevel();
    }

    public static int getXPForLvl(int level)
    {
        if (level < 0) return Integer.MAX_VALUE;
        if (level <= 16) return level * level + 6 * level;
        if (level <= 31) return (int) (level * level * 2.5D - 40.5D * level + 360.0D);

        return (int) (level * level * 4.5D - 162.5D * level + 2220.0D);
    }

    public static int getLvlForXP(int totalXP)
    {
        int result = 0;

        while (getXPForLvl(result) <= totalXP)
        {
            result++;
        }

        return --result;
    }
}
