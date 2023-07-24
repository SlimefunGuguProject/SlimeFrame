package me.voper.slimeframe.slimefun.utils;

import me.voper.slimeframe.utils.Utils;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Lore {

    private static final DecimalFormat format = new DecimalFormat("###,###,###.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    @Nonnull
    public static String production(int production) {
        return "&8⇨ &b⚡ &7产量：&b" + production + 'x';
    }

    @Nonnull
    public static String speed(int speed) {
        return "&8⇨ &b⚡ &7速度：&b" + speed + 'x';
    }

    @Nonnull
    public static String bonusPower(int power) {
        return "&8⇨ &e⚡ &7额外能量：" + format.format(Utils.energyPerTickToSeconds(power)) + " J/s";
    }

    @Nonnull
    public static String powerPerSecond(int power) {
        return power(Utils.energyPerTickToSeconds(power), "/s");
    }

    @Nonnull
    public static String powerBuffer(int power) {
        return power(power, " 可储存");
    }

    @Nonnull
    public static String power(int power, @Nonnull String suffix) {
        return "&8⇨ &e⚡ &7" + format.format(power) + " J" + suffix;
    }

    @Nonnull
    public static String usesLeft(int usesLeft) {
        return "&7还可使用 &e" + usesLeft + " &7次";
    }

}