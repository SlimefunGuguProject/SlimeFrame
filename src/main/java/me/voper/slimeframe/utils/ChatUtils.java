package me.voper.slimeframe.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.core.managers.SettingsManager;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@UtilityClass
public final class ChatUtils {

    public static boolean HEX_COLOR_SUPPORT;
    public static final String PREFIX = SlimeFrame.getSettingsManager().getString(SettingsManager.ConfigField.PREFIX);
    public static final boolean PREFIX_ENABLED = SlimeFrame.getSettingsManager().getBoolean(SettingsManager.ConfigField.PREFIX_ENABLED);
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{6})");

    public static String getColorByChar(char character) {
        ChatColor color = ChatColor.getByChar(character);
        return color != null ? color.toString() : Character.toString(character);
    }

    public static String parseColors(@Nonnull String text) {
        if (HEX_COLOR_SUPPORT) {
            Matcher matcher = HEX_COLOR_PATTERN.matcher(text);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
            }

            text = matcher.appendTail(buffer).toString();
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String stripColors(String text) {
        if (HEX_COLOR_SUPPORT) {
            Matcher matcher = HEX_COLOR_PATTERN.matcher(text);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                matcher.appendReplacement(buffer, "");
            }

            text = matcher.appendTail(buffer).toString();
        }

        return oldStripColors(text);
    }

    public static String getLastColorCode(String msg) {
        if (msg.length() < 2) {
            return "";
        } else {
            String one = msg.substring(msg.length() - 2, msg.length() - 1);
            String two = msg.substring(msg.length() - 1);
            if (one.equals("§")) {
                return one + two;
            } else {
                return one.equals("&") ? getColorByChar(two.charAt(0)) : "";
            }
        }
    }

    private static String oldStripColors(String text) {
        return text.replaceAll("[&][0-9A-Fa-fk-orx]", "").replaceAll(String.valueOf('Â'), "").replaceAll("[§][0-9A-Fa-fk-orx]", "");
    }

    @Nonnull
    public static String getLastColors(@Nonnull String input) {
        StringBuilder result = new StringBuilder();
        int length = input.length();

        for (int index = length - 1; index > -1; --index) {
            boolean found = false;
            String color = String.valueOf(input.charAt(index));
            if ("0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".contains(color) && index - 1 >= 0) {
                char section = input.charAt(index - 1);
                if (section == 167) {
                    --index;
                    result.insert(0, section + color);
                    found = true;
                }
            }

            if (!found && result.length() != 0) {
                break;
            }
        }

        return result.toString();
    }

    public static void applyLastColorToFollowingLines(@Nonnull List<String> lines) {
        if (lines.get(0).length() == 0 || lines.get(0).charAt(0) != 167) {
            lines.set(0, ChatColor.WHITE + lines.get(0));
        }

        for (int i = 1; i < lines.size(); ++i) {
            String pLine = lines.get(i - 1);
            String subLine = lines.get(i);
            if (subLine.length() == 0 || subLine.charAt(0) != 167) {
                lines.set(i, getLastColors(pLine) + subLine);
            }
        }

    }

    public static void sendMessage(CommandSender sender, @Nonnull String text) {
        if (sender instanceof Player p) {
            p.sendMessage(parseColors(text));
        } else {
            sender.sendMessage(text);
        }
    }

    public static void sendMessage(Player p, @Nonnull List<String> text) {
        for (String m : text) {
            if (PREFIX_ENABLED) {
                p.sendMessage(parseColors(m.isBlank() ? m : PREFIX + m));
                continue;
            }
            p.sendMessage(parseColors(m));
        }
    }

    public static void sendMessage(Player p, String... strings) {
        sendMessage(p, List.of(strings));
    }

    public static void sendMessage(Player p, BaseComponent... components) {
        p.spigot().sendMessage(new ComponentBuilder(PREFIX_ENABLED ? PREFIX : "").append(components).create());
    }

    public static void sendMessage(Player p, String text) {
        sendMessage(p, List.of(text));
    }


    static {
        try {
            ChatColor.class.getDeclaredMethod("of", String.class);
            HEX_COLOR_SUPPORT = true;
        } catch (NoSuchMethodException var1) {
            HEX_COLOR_SUPPORT = false;
        }

    }
}