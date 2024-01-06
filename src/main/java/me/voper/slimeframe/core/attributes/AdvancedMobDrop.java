package me.voper.slimeframe.core.attributes;

import io.github.thebusybiscuit.slimefun4.core.attributes.RandomMobDrop;
import org.bukkit.entity.EntityType;

// An alternative version of the RandomMobDrop interface that provides a bit more flexibility
public interface AdvancedMobDrop extends RandomMobDrop {
    @Override
    default int getMobDropChance() { return 0; }
    int getMobDropChance(EntityType entity);
}
