package me.voper.slimeframe.core.attributes;

/*
* Every SlimefunItem that implements this interface
* will cause a freezing effect a specified amount of damage
* per second defined by the getFreezingDamage method
*/
public interface FreezingItem {
    double getFreezingDamage();
}
