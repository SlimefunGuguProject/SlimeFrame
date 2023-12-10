package me.voper.slimeframe.listeners;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.utils.AutoUpdater;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;

public class WarnOperatorsListener implements Listener {

    private final SlimeFrame plugin;
    private final BaseComponent[] components;

    public WarnOperatorsListener(@Nonnull SlimeFrame plugin, String version) {
        this.plugin = plugin;
        this.components = new ComponentBuilder()
                .append("\nA ").color(ChatColor.AQUA)
                .append("MAJOR ").color(ChatColor.DARK_RED)
                .append("version for SlimeFrame is available - v" + version + "\n").color(ChatColor.AQUA)
                .append("Make sure to check the changelog for breaking changes\n").color(ChatColor.AQUA)
                .append("Click here to download\n").color(ChatColor.AQUA)
                .event(new ClickEvent(ClickEvent.Action.OPEN_URL, AutoUpdater.RELEASES))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Go to download page")))
                .create();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                p.spigot().sendMessage(components);
                p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            }, 3 * 20L);
        }
    }

}
