package me.voper.slimeframe.implementation.items.gear;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import io.github.thebusybiscuit.slimefun4.implementation.items.armor.SlimefunArmorPiece;
import me.voper.slimeframe.core.attributes.FreezingProtection;
import me.voper.slimeframe.implementation.groups.Groups;
import me.voper.slimeframe.utils.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class CryogenicArmorPiece extends SlimefunArmorPiece implements ProtectiveArmor, FreezingProtection {

    private final NamespacedKey namespacedKey;

    public CryogenicArmorPiece(SlimefunItemStack item, ItemStack[] recipe, PotionEffect[] potionEffects) {
        super(Groups.GEAR, item, RecipeType.ARMOR_FORGE, recipe, potionEffects);
        this.namespacedKey = Keys.createKey("cryogenic_suit");
    }

    public CryogenicArmorPiece(SlimefunItemStack item, ItemStack[] recipe) {
        this(item, recipe, new PotionEffect[0]);
    }

    @Nonnull
    @Override
    public ProtectionType[] getProtectionTypes() {
        return new ProtectionType[]{ProtectionType.RADIATION, ProtectionType.BEES};
    }

    @Override
    public boolean isFullSetRequired() {
        return true;
    }

    @Nullable
    @Override
    public NamespacedKey getArmorSetId() {
        return namespacedKey;
    }
}
