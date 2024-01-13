package me.voper.slimeframe.api.events;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a {@link Player} opens a {@link me.voper.slimeframe.implementation.items.relics.Relic}.
 *
 * @author VoperAD
 */
public class PlayerOpenRelicEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private ItemStack reward;
    private final ItemStack relic;
    private int voidTracesReward;
    private boolean isCancelled;

    public PlayerOpenRelicEvent(Player player, ItemStack reward, ItemStack relic, int voidTracesReward) {
        this.player = player;
        this.reward = reward;
        this.relic = relic;
        this.voidTracesReward = voidTracesReward;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getReward() {
        return reward;
    }

    public void setReward(ItemStack reward) {
        this.reward = reward;
    }

    public int getVoidTracesReward() {
        return voidTracesReward;
    }

    public void setVoidTracesReward(int voidTracesReward) {
        this.voidTracesReward = voidTracesReward;
    }

    public ItemStack getRelic() {
        return this.relic;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
