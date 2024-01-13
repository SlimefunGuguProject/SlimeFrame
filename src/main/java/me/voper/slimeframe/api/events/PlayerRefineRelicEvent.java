package me.voper.slimeframe.api.events;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import me.voper.slimeframe.implementation.items.relics.Relic;

/**
 * Called when a {@link Player} refines a {@link me.voper.slimeframe.implementation.items.relics.Relic}.
 *
 * @author VoperAD
 */
public class PlayerRefineRelicEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ItemStack relic;
    private Relic.Refinement newRefinement;
    private boolean isCancelled;

    public PlayerRefineRelicEvent(Player player, ItemStack relic, Relic.Refinement newRefinement) {
        this.player = player;
        this.relic = relic;
        this.newRefinement = newRefinement;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getRelic() {
        return this.relic;
    }

    public Relic.Refinement getNewRefinement() {
        return this.newRefinement;
    }

    public void setNewRefinement(Relic.Refinement newRefinement) {
        this.newRefinement = newRefinement;
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
