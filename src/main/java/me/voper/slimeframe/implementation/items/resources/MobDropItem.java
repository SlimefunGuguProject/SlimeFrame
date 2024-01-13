package me.voper.slimeframe.implementation.items.resources;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.core.attributes.AdvancedMobDrop;
import me.voper.slimeframe.utils.Keys;
import me.voper.slimeframe.utils.Utils;

import lombok.Getter;
import net.guizhanss.guizhanlib.minecraft.helper.MaterialHelper;
import net.md_5.bungee.api.ChatColor;

// An item dropped by mobs. The probability of an item dropping from a mob can vary depending on the mob
@Getter
public class MobDropItem extends SlimefunItem implements AdvancedMobDrop, RecipeDisplayItem {

    public static final RecipeType RECIPE_TYPE = new RecipeType(Keys.createKey("mob_drop_recipe"), new CustomItemStack(Material.IRON_SWORD, "&rKill the specified Mobs to obtain this Item"));

    private int globalDrobChance = 0;
    private Map<EntityType, Integer> mobChanceMap = new HashMap<>();

    public MobDropItem(ItemGroup itemGroup, SlimefunItemStack item) {
        super(itemGroup, item, RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);
    }

    @Override
    public void preRegister() {
        super.preRegister();
        addItemHandler((ItemUseHandler) PlayerRightClickEvent::cancel);
    }

    @Override
    public void register(@Nonnull SlimefunAddon addon) {
        Validate.notEmpty(mobChanceMap, "The method setMobChanceMap must be called before registering the item");
        for (EntityType mob : mobChanceMap.keySet()) {
            Set<ItemStack> drops = Slimefun.getRegistry().getMobDrops().getOrDefault(mob, new HashSet<>());
            drops.add(getItem());
            Slimefun.getRegistry().getMobDrops().put(mob, drops);
        }
        super.register(addon);
    }

    public MobDropItem setMobs(List<EntityType> mobList) {
        this.mobChanceMap.putAll(mobList.stream()
                .collect(Collectors.toMap(mob -> mob, mob -> globalDrobChance)));

        return this;
    }

    public MobDropItem setMobChanceMap(@Nonnull Map<EntityType, Integer> mobChanceMap) {
        this.mobChanceMap = new HashMap<>(mobChanceMap);
        return this;
    }

    public MobDropItem setDropChance(EntityType entity, int dropChance) {
        mobChanceMap.put(entity, dropChance);
        return this;
    }

    public MobDropItem setDropChance(int chance) {
        mobChanceMap.replaceAll((entityType, integer) -> chance);
        this.globalDrobChance = chance;
        return this;
    }

    @Override
    public int getMobDropChance() {
        return globalDrobChance;
    }

    @Override
    public int getMobDropChance(EntityType entity) {
        return mobChanceMap.getOrDefault(entity, 0);
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7⇩ 该物品由以下生物掉落 ⇩";
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        final List<ItemStack> recipes = new ArrayList<>();
        mobChanceMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    recipes.add(new CustomItemStack(Material.PAPER,
                        ChatColor.WHITE + "掉落几率: " + ChatColor.AQUA + entry.getValue() + "%"));
                    Material material = Material.getMaterial(entry.getKey().name() + "_SPAWN_EGG");
                    if (material != null) {
                        CustomItemStack spawnEgg = new CustomItemStack(material, MaterialHelper.getName(material));
                        recipes.add(spawnEgg);
                    }
                });

        return recipes;
    }

}
