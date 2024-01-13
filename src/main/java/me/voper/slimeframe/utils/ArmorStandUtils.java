package me.voper.slimeframe.utils;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArmorStandUtils {

    public static @Nonnull ArmorStand spawnArmorStand(@Nonnull Location location) {
        return location.getWorld().spawn(location, ArmorStand.class, armorStand -> {
            armorStand.setVisible(false);
            armorStand.setSilent(true);
            armorStand.setMarker(true);
            armorStand.setGravity(false);
            armorStand.setBasePlate(false);
            armorStand.setRemoveWhenFarAway(false);
        });
    }

}
