package me.voper.slimeframe.slimefun.items.components;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.slimefun.SFrameStacks;
import me.voper.slimeframe.slimefun.SFrameTheme;
import me.voper.slimeframe.slimefun.groups.Groups;
import me.voper.slimeframe.slimefun.items.relics.RelicItemStack;
import me.voper.slimeframe.slimefun.utils.HeadTextures;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class UtilsComponents {

    public static final Map<SlimefunItemStack, UtilsComponents> COMPONENTS_MAP = new HashMap<>();

    private SlimefunItemStack voidShardEssence;
    private SlimefunItemStack temporalCogwheel;
    private SlimefunItemStack neuralNexusCore;

    public UtilsComponents(SlimefunItemStack item) {
        this.voidShardEssence = createVoidShard(item);
        this.temporalCogwheel = createTemporal(item);
        this.neuralNexusCore = createNeuralNexus(item);
        COMPONENTS_MAP.put(item, this);
    }

    public UtilsComponents() {}

    public static SlimefunItemStack createVoidShard(SlimefunItemStack item) {
        if (COMPONENTS_MAP.get(item) != null && COMPONENTS_MAP.get(item).getVoidShardEssence() != null) {
            return COMPONENTS_MAP.get(item).getVoidShardEssence();
        }

        String displayName = ChatColor.stripColor(item.getDisplayName()).replace("Prime", "");
        String itemId = item.getItemId();

        SlimefunItemStack voidShardEssence = SFrameTheme.sfStackFromTheme(
                itemId + "_VSE",
                HeadTextures.VOID_SHARD_ESSENCE,
                SFrameStacks.PRIME_COMPONENTS_THEME.withNameColor(Colors.BRONZE),
                displayName + "虚空碎片",
                "一块浓缩的虚空能量碎片，",
                "蕴含着未开发的力量。",
                "这种精华散发着超凡脱俗的气息，",
                "是制作 Prime 物品的必要材料。",
                "用于合成" + item.getDisplayName()
        );

        UtilsComponents utilsComponents = COMPONENTS_MAP.get(item);
        if (utilsComponents == null) {
            COMPONENTS_MAP.put(item, new UtilsComponents().setVoidShardEssence(voidShardEssence));
        } else if (utilsComponents.getVoidShardEssence() == null) {
            COMPONENTS_MAP.put(item, utilsComponents.setVoidShardEssence(voidShardEssence));
        }
        return voidShardEssence;
    }

    public static SlimefunItemStack createTemporal(SlimefunItemStack item) {
        if (COMPONENTS_MAP.get(item) != null && COMPONENTS_MAP.get(item).getTemporalCogwheel() != null) {
            return COMPONENTS_MAP.get(item).getTemporalCogwheel();
        }

        String displayName = ChatColor.stripColor(item.getDisplayName()).replace("Prime", "");
        String itemId = item.getItemId();

        SlimefunItemStack temporalCog = SFrameTheme.sfStackFromTheme(
                itemId + "_TC",
                HeadTextures.TEMPORAL_COGWHEEL,
                SFrameStacks.PRIME_COMPONENTS_THEME.withNameColor(Colors.SILVER),
                displayName + "时间齿轮",
                "精密制作的齿轮注入了时间能量，",
                "可与时间的流动产生共鸣。",
                "其复杂的设计允许其",
                "操控时间的力量。",
                "用于合成：" + item.getDisplayName()
        );

        UtilsComponents utilsComponents = COMPONENTS_MAP.get(item);
        if (utilsComponents == null) {
            COMPONENTS_MAP.put(item, new UtilsComponents().setTemporalCogwheel(temporalCog));
        } else if (utilsComponents.getTemporalCogwheel() == null) {
            COMPONENTS_MAP.put(item, utilsComponents.setTemporalCogwheel(temporalCog));
        }
        return temporalCog;
    }

    public static SlimefunItemStack createNeuralNexus(SlimefunItemStack item) {
        if (COMPONENTS_MAP.get(item) != null && COMPONENTS_MAP.get(item).getNeuralNexusCore() != null) {
            return COMPONENTS_MAP.get(item).getNeuralNexusCore();
        }

        String displayName = ChatColor.stripColor(item.getDisplayName()).replace("Prime", "");
        String itemId = item.getItemId();

        SlimefunItemStack neuralNexus = SFrameTheme.sfStackFromTheme(
                itemId + "_NNC",
                HeadTextures.NEURAL_NEXUS_CORE,
                SFrameStacks.PRIME_COMPONENTS_THEME.withNameColor(Colors.GOLD_2),
                displayName + "神经网络核心",
                "注入神经通路和突触电路的专用核心，",
                "利用了先进 Warframe 科技的力量。",
                "用于合成：" + item.getDisplayName()
        );

        UtilsComponents utilsComponents = COMPONENTS_MAP.get(item);
        if (utilsComponents == null) {
            COMPONENTS_MAP.put(item, new UtilsComponents().setNeuralNexusCore(neuralNexus));
        } else if (utilsComponents.getNeuralNexusCore() == null) {
            COMPONENTS_MAP.put(item, utilsComponents.setNeuralNexusCore(neuralNexus));
        }
        return neuralNexus;
    }

    public static void registerAll(SlimeFrame plugin) {
        COMPONENTS_MAP.forEach((slimefunItemStack, components) -> {
            try {
                register(components, plugin);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private static void register(UtilsComponents components, SlimeFrame plugin) throws IllegalAccessException {
        UnplaceableBlock neuralNex = new UnplaceableBlock(Groups.PRIME_COMPONENTS, components.getNeuralNexusCore(), PrimeComponents.RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);
        UnplaceableBlock temporalCog = new UnplaceableBlock(Groups.PRIME_COMPONENTS, components.getTemporalCogwheel(), PrimeComponents.RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);
        UnplaceableBlock voidShard = new UnplaceableBlock(Groups.PRIME_COMPONENTS, components.getVoidShardEssence(), PrimeComponents.RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);

        List<ItemStack> recipeNeuralNex = new ArrayList<>(9);
        List<ItemStack> recipeTempCog = new ArrayList<>(9);
        List<ItemStack> recipeVoidShard = new ArrayList<>(9);

        for (Field field: SFrameStacks.class.getDeclaredFields()) {
            if (field.getType() != RelicItemStack.class) continue;
            RelicItemStack relic = (RelicItemStack) field.get(null);

            for (SlimefunItemStack common: relic.getCommonDrops()) {
                if (!SlimefunUtils.isItemSimilar(components.getVoidShardEssence(), common, true)) continue;
                recipeVoidShard.add(relic);
            }

            for (SlimefunItemStack uncommon: relic.getUncommonDrops()) {
                if (!SlimefunUtils.isItemSimilar(components.getTemporalCogwheel(), uncommon, true)) continue;
                recipeTempCog.add(relic);
            }

            if (SlimefunUtils.isItemSimilar(components.getNeuralNexusCore(), relic.getRareDrop(), true)) {
                recipeNeuralNex.add(relic);
            }
        }

        neuralNex.setRecipe(recipeNeuralNex.toArray(new ItemStack[9]));
        temporalCog.setRecipe(recipeTempCog.toArray(new ItemStack[9]));
        voidShard.setRecipe(recipeVoidShard.toArray(new ItemStack[9]));

        neuralNex.register(plugin);
        temporalCog.register(plugin);
        voidShard.register(plugin);
    }

}
