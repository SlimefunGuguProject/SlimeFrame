package me.voper.slimeframe.slimefun.researches;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.managers.SettingsManager;
import me.voper.slimeframe.slimefun.SFrameItems;
import me.voper.slimeframe.slimefun.SFrameStacks;
import me.voper.slimeframe.slimefun.groups.Groups;
import me.voper.slimeframe.slimefun.items.resources.SpecialOre;
import me.voper.slimeframe.utils.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public final class Researches {

    private static final int FIRST_RESEARCH_ID = 131300000;
    private static final SettingsManager sm = SlimeFrame.getSettingsManager();

    public static void setup() {
        if (!sm.getBoolean(SettingsManager.ConfigField.RESEARCHES_ENABLED)) return;

        create(Keys.ORES_RESEARCH, 1, "粘液战甲矿石", 50, SpecialOre.SOURCE_ORE_MAP.values().toArray(SpecialOre[]::new));
        create(Keys.ALLOYS_RESEARCH, 2, "揭开稀有合金的秘密", 50, SFrameItems.ALLOYS.toArray(SlimefunItem[]::new));

        create(Keys.NOSAM_RESEARCH, 3, "诺萨姆切割器", 30, SFrameStacks.NOSAM_PICK);
        create(Keys.FOCUSED_NOSAM_RESEARCH, 4, "可以更加熟练地使用诺萨姆切割器", 60, SFrameStacks.FOCUSED_NOSAM_PICK);
        create(Keys.PRIME_NOSAM_RESEARCH, 5, "精通诺萨姆切割器", 90, SFrameStacks.PRIME_NOSAM_PICK);

        create(Keys.MACHINES_RESEARCH, 6, "使用基本机器", 30, Groups.MACHINES.getItems().stream()
                .filter(item -> !item.getItemName().contains("Prime") && !item.getItemName().contains("高级"))
                .toList());

        create(Keys.ADV_MACHINES_RESEARCH, 7, "使用高级机器", 50, Groups.MACHINES.getItems().stream()
                .filter(item -> item.getItemName().contains("高级"))
                .toList());

        create(Keys.PRIME_MACHINES_RESEARCH, 8, "使用 Prime 机器", 60, Groups.MACHINES.getItems().stream()
                .filter(item -> item.getItemName().contains("Prime"))
                .toList());

        create(Keys.CRYO_SUIT_RESEARCH, 9, "释放低温技术的潜力", 50,
            SFrameStacks.CRYO_BOOTS, SFrameStacks.CRYO_CHESTPLATE, SFrameStacks.CRYO_HELMET, SFrameStacks.CRYO_LEGGINGS);
        create(Keys.ALLOY_PLATES_RESEARCH, 10, "研究复杂的合金版，是高级装备的重要组件", 30, SFrameItems.ALLOY_PLATES_MAP.values().toArray(new SlimefunItemStack[0]));

        create(Keys.ENERGY_GEN_RESEARCH, 11, "利用高级发电机", 40,
                SFrameStacks.GRAVITECH_ENERCELL, SFrameStacks.ARCANE_FLUX_DYNAMO, SFrameStacks.SPECTRA_REACTOR,
                SFrameStacks.PRISMA_POWER_CORE, SFrameStacks.VOIDFORGE_CELESTIUM_GENERATOR, SFrameStacks.AXIOM_ENERGENESIS_ENGINE,
                SFrameStacks.CHRONOS_INFINITY_DYNAMO, SFrameStacks.PRIMORDIAL_ETERNACORE_REACTOR, SFrameStacks.VOIDLIGHT_FUSION_GENERATOR);

        create(Keys.ASTRAL_GEN_RESEARCH, 12, "使用 Prime 发电机主宰能量", 50, SFrameStacks.ASTRAL_PRIME_GENERATOR);
        create(Keys.MULTIBLOCKS_RESEARCH, 13, "Comprehending the complexities of SlimeFrame Multiblocks", 30, SFrameStacks.FOUNDRY);
        create(Keys.CONDENSED_PLATE_RESEARCH, 14, "揭开浓缩板的秘密", 40,
            SFrameStacks.CONDENSED_PLATE);

        create(Keys.GENERAL_RESOURCES_RESEARCH, 15 , "粘液战甲通用资源", 25,
                SFrameStacks.CRYOTIC, SFrameStacks.COOLANT_CANISTER, SFrameStacks.TELLURIUM_FRAGMENT, SFrameStacks.TELLURIUM,
                SFrameStacks.DILUTED_THERMIA, SFrameStacks.RUBEDO, SFrameStacks.ARGON_CRYSTAL, SFrameStacks.CUBIC_DIODES,
                SFrameStacks.PLASTIDS, SFrameStacks.CONTROL_MODULE, SFrameStacks.GALLIUM, SFrameStacks.SALVAGE,
                SFrameStacks.PRISMATIC_ENERGIZED_CORE, SFrameStacks.MORPHICS, SFrameStacks.NEURAL_SENSORS, SFrameStacks.NEURODES,
                SFrameStacks.OROKIN_CELL);

    }

    private static void create(NamespacedKey key, int id, String name, int cost, ItemStack... itemStacks) {
         new Research(key, FIRST_RESEARCH_ID + id, name, cost).addItems(itemStacks).register();
    }

    private static void create(NamespacedKey key, int id, String name, int cost, SlimefunItem... items) {
        Research research = new Research(key, FIRST_RESEARCH_ID + id, name, cost);
        research.addItems(items);
        research.register();
    }

    private static void create(NamespacedKey key, int id, String name, int cost, List<SlimefunItem> items) {
        create(key, id, name, cost, items.toArray(new SlimefunItem[0]));
    }

}
