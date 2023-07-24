package me.voper.slimeframe.slimefun.items.tools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.managers.SettingsManager;
import me.voper.slimeframe.slimefun.datatypes.MerchantRecipeListDataType;
import me.voper.slimeframe.utils.ChatUtils;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.Keys;
import me.voper.slimeframe.utils.Utils;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractVillager;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class MerchantSoulContract extends SimpleSlimefunItem<EntityInteractHandler> {

    private static final String AE_ITEM_IDENTIFIER = "internal=H4sIAAAAAAAA/";

    private static final Predicate<MerchantRecipe> REMOVE_AE_TRADES = (merchantRecipe) -> {
        ItemStack result = merchantRecipe.getResult();
        if (SlimeFrame.getSettingsManager().getBoolean(SettingsManager.ConfigField.ENABLE_AE_ITEMS_IN_SOUL_CONTRACTS) || !result.hasItemMeta()) return false;
        ItemMeta itemMeta = result.getItemMeta();
        return itemMeta.toString().contains(AE_ITEM_IDENTIFIER);
    };

    public MerchantSoulContract(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.disenchantable = false;
        this.enchantable = false;
        setUseableInWorkbench(false);
    }

    @Nonnull
    @Override
    public EntityInteractHandler getItemHandler() {
        return (e, item, offhand) -> {
            if (e.isCancelled() || !Slimefun.getProtectionManager().hasPermission(e.getPlayer(), e.getRightClicked().getLocation(), Interaction.INTERACT_ENTITY) || !Slimefun.getProtectionManager().hasPermission(e.getPlayer(), e.getRightClicked().getLocation(), Interaction.ATTACK_ENTITY)) {
                // They don't have permission to use it in this area
                return;
            }

            if (e.getRightClicked() instanceof AbstractVillager merchant) {
                ItemMeta meta = item.getItemMeta();
                if (meta == null) {
                    e.setCancelled(true);
                    return;
                }

                if (PersistentDataAPI.has(meta, Keys.MERCHANT_RECIPE, new MerchantRecipeListDataType())) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("已签订过契约！");
                    return;
                }

                final List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
                if (recipes.isEmpty()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage("该商人没有任何可用交易！");
                    return;
                }

                // Removing every trade that has a AdvancedEnchantment item involved according to the config
                recipes.removeIf(REMOVE_AE_TRADES);

                // Filtering each recipe to remove any Material.AIR from their ingredients
                for (MerchantRecipe recipe: recipes) {
                    List<ItemStack> ingredients = recipe.getIngredients().stream()
                            .filter(itemStack -> itemStack.getType() != Material.AIR)
                            .toList();

                    recipe.setIngredients(ingredients);
                }

                PersistentDataAPI.set(meta, Keys.MERCHANT_RECIPE, new MerchantRecipeListDataType(), recipes);

                // Update the meta
                meta.addEnchant(Enchantment.LUCK, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GREEN + "交易：");
                lore.add(" ");

                recipes.forEach(recipe -> {
                    List<String> ingredientsNames = recipe.getIngredients().stream()
                            .filter(itemStack -> itemStack.getItemMeta() != null)
                            .map(itemStack -> itemStack.getAmount() + " " + ItemStackHelper.getDisplayName(itemStack)
                            )
                            .toList();

                    ItemMeta resultMeta = recipe.getResult().getItemMeta();
                    if (resultMeta == null) return;
                    String resultName = ItemStackHelper.getDisplayName(recipe.getResult());
                    resultName = recipe.getResult().getAmount() + " " + resultName;

                    lore.add(ChatColor.WHITE + String.valueOf(recipes.indexOf(recipe) + 1) + " - " +
                            ChatColor.BLUE + String.join(" + ", ingredientsNames) +
                            ChatColor.DARK_PURPLE + " -> " +
                            Colors.ORANGE + resultName
                    );
                });

                meta.setLore(lore);
                item.setItemMeta(meta);


                double offset = ThreadLocalRandom.current().nextDouble(0.5);

                merchant.getWorld().playSound(merchant.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 1, 1.4F);
                merchant.getWorld().spawnParticle(Particle.CRIMSON_SPORE, merchant.getLocation(), 10, 0, offset / 2, 0, 0);
                merchant.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, merchant.getLocation(), 5, 0.04, 1, 0.04);

                e.setCancelled(true);
                merchant.setHealth(0);
                ChatUtils.sendMessage(e.getPlayer(), "契约已订好！");
            }
        };
    }

}