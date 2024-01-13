package me.voper.slimeframe.implementation.items.resources;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.handlers.ItemConsumptionHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.implementation.SFrameStacks;
import me.voper.slimeframe.implementation.groups.Groups;

import net.md_5.bungee.api.ChatColor;

public class CoolantCanister extends SimpleSlimefunItem<ItemConsumptionHandler> {

    public CoolantCanister() {
        super(Groups.RESOURCES, SFrameStacks.COOLANT_CANISTER, MobDropItem.RECIPE_TYPE, new ItemStack[]{
                null, null, null, null,
                new CustomItemStack(Material.CAVE_SPIDER_SPAWN_EGG, ChatColor.BLUE + "冷却蛛形机",
                        "",
                        ChatColor.GRAY + "带蓝色粒子的洞穴蜘蛛")
        });
    }

    @Nonnull
    @Override
    public ItemConsumptionHandler getItemHandler() {
        return ((event, player, itemStack) -> {
            player.sendMessage(ChatColor.BLUE + "你的身体要被冻结了...");
            player.setFreezeTicks(140 + 10 * 20);
            player.setHealth(player.getHealth() / 10);
        });
    }
}
