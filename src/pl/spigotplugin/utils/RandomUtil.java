package pl.spigotplugin.utils;

import org.apache.commons.lang.Validate;

import java.util.Random;

public class RandomUtil {
    private static final Random rand = new Random();

    public static int getRandInt(int min, int max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return RandomUtil.rand.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble(double min, double max) throws IllegalArgumentException {
        Validate.isTrue(max > min, "Max can't be smaller than min!");
        return RandomUtil.rand.nextDouble() * (max - min) + min;
    }

    public static int getRandInteger(final int min, final int max) throws IllegalArgumentException {
        return RandomUtil.rand.nextInt(max - min + 1) + min;
    }

    public static boolean getChance(double chance) {
        return chance >= 100.0 || chance >= getRandDouble(0.0, 100.0);
    }
}
