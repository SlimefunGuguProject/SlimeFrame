package me.voper.slimeframe.implementation;

import com.cryptomorin.xseries.XMaterial;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import lombok.experimental.UtilityClass;
import me.voper.slimeframe.implementation.groups.Groups;
import me.voper.slimeframe.implementation.items.components.PrimeComponents;
import me.voper.slimeframe.implementation.items.components.UtilsComponents;
import me.voper.slimeframe.implementation.items.relics.Relic;
import me.voper.slimeframe.implementation.items.relics.RelicItemStack;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.HeadTextures;
import me.voper.slimeframe.utils.Lore;
import me.voper.slimeframe.utils.RandomItemStacks;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public final class SFrameStacks {

    public static final SFrameTheme RESOURCES_THEME = new SFrameTheme(ChatColor.AQUA, Groups.RESOURCES_NAME);
    public static final SFrameTheme MACHINES_THEME = new SFrameTheme(ChatColor.AQUA, Groups.MACHINES_NAME);
    public static final SFrameTheme GENERATORS_THEME = new SFrameTheme(ChatColor.AQUA, Groups.GENERATORS_NAME);
    public static final SFrameTheme UTILS_TOOLS_THEME = new SFrameTheme(ChatColor.AQUA, Groups.UTILS_AND_TOOLS_NAME);
    public static final SFrameTheme GEAR_THEME = new SFrameTheme(ChatColor.AQUA, Groups.GEAR_NAME);
    public static final SFrameTheme PRIME_COMPONENTS_THEME = new SFrameTheme(ChatColor.GOLD, Groups.PRIME_COMPONENTS_NAME);

    @Nonnull
    @Contract("_ -> new")
    public static ItemStack enchantedItem(@Nonnull Material m) {
        return new CustomItemStack(m, (meta) -> {
            meta.addEnchant(Enchantment.LUCK, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }

    @Nonnull
    private static ItemStack createPotion(@Nonnull PotionType type, @Nonnull Material recipient) {
        ItemStack potion = new ItemStack(recipient);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        if (meta != null) {
            meta.setBasePotionData(new PotionData(type));
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            potion.setItemMeta(meta);
        }
        return potion;
    }

    @Nonnull
    public static ItemStack createLingeringPotion(@Nonnull PotionType type) {
        return createPotion(type, Material.LINGERING_POTION);
    }

    @Nonnull
    public static ItemStack createPotion(@Nonnull PotionType type) {
        return createPotion(type, Material.POTION);
    }

    public static final RandomItemStacks<RelicItemStack> RANDOM_LITH_RELICS = new RandomItemStacks<>();
    public static final RandomItemStacks<RelicItemStack> RANDOM_MESO_RELICS = new RandomItemStacks<>();
    public static final RandomItemStacks<RelicItemStack> RANDOM_NEO_RELICS = new RandomItemStacks<>();
    public static final RandomItemStacks<RelicItemStack> RANDOM_AXI_RELICS = new RandomItemStacks<>();

    // ---- Utils and Tools ----

    public static final SlimefunItemStack NOSAM_PICK = SFrameTheme.sfStackFromTheme(
            "WF_NOSAM_PICK",
            Material.IRON_PICKAXE,
            UTILS_TOOLS_THEME.withNameColor(Colors.BRONZE),
            "诺萨姆切割器",
            "一把特殊的镐子，",
            "可从岩石与矿石中采集资源。"
    );

    public static final SlimefunItemStack FOCUSED_NOSAM_PICK = SFrameTheme.sfStackFromTheme(
            "WF_FOC_NOSAM_PICK",
            Material.DIAMOND_PICKAXE,
            UTILS_TOOLS_THEME.withNameColor(Colors.SILVER),
            "聚焦诺萨姆切割器",
            "改进版聚焦诺萨姆切割器，",
            "拥有更高的稀有资源获取几率。"
    );

    public static final SlimefunItemStack PRIME_NOSAM_PICK = SFrameTheme.sfStackFromTheme(
            "WF_PRIME_NOSAM_PICK",
            Material.NETHERITE_PICKAXE,
            UTILS_TOOLS_THEME.withNameColor(Colors.GOLD_2),
            "高级诺萨姆切割器",
            "诺萨姆切割器的最终版本，",
            "可以经常获得稀有资源。"
    );

    public static final SlimefunItemStack MERCHANT_SOUL_CONTRACT = SFrameTheme.sfStackFromTheme(
            "WF_SOUL_CONTRACT",
            Material.PAPER,
            UTILS_TOOLS_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "商人灵魂契约",
            ChatColor.GREEN + "右键点击" + ChatColor.WHITE + "一名商人",
            ChatColor.WHITE + "以签订契约。",
            "",
            ChatColor.DARK_RED + "警告：" + Colors.ORANGE + "这会杀死商人。"
    );

    public static final SlimefunItemStack OROKIN_WAND = SFrameTheme.sfStackFromTheme(
            "WF_OROKIN_WAND",
            Material.STICK,
            UTILS_TOOLS_THEME,
            "Orokin 权杖",
            "该权杖拥有收集部分特殊方块的能力。",
            "例如：强化深板岩、紫水晶母岩。",
            "",
            Lore.usesLeft(32)
    );

    public static final SlimefunItemStack PRIME_OROKIN_WAND = SFrameTheme.sfStackFromTheme(
            "WF_PRIME_OROKIN_WAND",
            Material.BLAZE_ROD,
            UTILS_TOOLS_THEME.withNameColor(ChatColor.YELLOW),
            "Orokin 权杖 Prime",
            "改进版本的 Orokin 权杖。",
            "",
            Lore.usesLeft(1024)
    );

    public static final SlimefunItemStack INPUT_CONFIGURATOR = SFrameTheme.sfStackFromTheme(
            "WF_INPUT_CONFIGURATOR",
            Material.BLAZE_ROD,
            UTILS_TOOLS_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "输入配置器",
            "将机器内输入物品的配置",
            "复制到同类型的机器中。",
            "",
            ChatColor.GREEN + "右键点击" + UTILS_TOOLS_THEME.getLoreColor() + "复制配置",
            ChatColor.DARK_GREEN + "蹲下+" + ChatColor.GREEN + "右键点击" + UTILS_TOOLS_THEME.getLoreColor() +
                "粘贴配置"
    );

    public static final SlimefunItemStack SELECTOR_CONFIGURATOR = SFrameTheme.sfStackFromTheme(
            "WF_SELECTOR_CONFIG",
            enchantedItem(Material.BLAZE_ROD),
            UTILS_TOOLS_THEME.withNameColor(Colors.FERRARI_RED),
            "选择配置器",
            "将机器内选择器的配置",
            "复制到同类型的机器中。",
            "",
            ChatColor.GREEN + "右键点击" + UTILS_TOOLS_THEME.getLoreColor() + "复制配置",
            ChatColor.DARK_GREEN + "蹲下+" + ChatColor.GREEN + "右键点击" + UTILS_TOOLS_THEME.getLoreColor() +
                "粘贴配置"
    );

    public static final SlimefunItemStack ITEM_PROJECTOR = SFrameTheme.sfStackFromTheme(
            "WF_ITEM_PROJECTOR",
            Material.BLACKSTONE_SLAB,
            UTILS_TOOLS_THEME,
            "物品投影仪",
            "这种由 Corpus 制作的设备",
            "可以为任何物品生成全息投影。"
    );

    public static final SlimefunItemStack ENERGY_CENTRAL = SFrameTheme.sfStackFromTheme(
            "WF_ENERGY_CENTRAL",
            Material.BEACON,
            UTILS_TOOLS_THEME.withNameColor(ChatColor.YELLOW),
            "能源中控",
            "一个可以显示能源网络信息的设备，",
            "例如总生产、存储与消耗，",
            "以及发电机与电容等。"
    );

    // ---- Gear ----

    private static final List<String> CRYOGENIC_SUIT_LORE = new ArrayList<>();

    static {
        CRYOGENIC_SUIT_LORE.add("");
        CRYOGENIC_SUIT_LORE.add(ChatColor.GOLD + "套装效果：");
        CRYOGENIC_SUIT_LORE.add(ChatColor.YELLOW + "- 辐射免疫");
        CRYOGENIC_SUIT_LORE.add(ChatColor.YELLOW + "- 蜜蜂蜇咬免疫");
        CRYOGENIC_SUIT_LORE.add(ChatColor.BLUE + "- 冰冻免疫");
        CRYOGENIC_SUIT_LORE.add("");
        CRYOGENIC_SUIT_LORE.add(Groups.GEAR_NAME);
    }

    public static final SlimefunItemStack CRYO_HELMET = new SlimefunItemStack("WF_CRYO_HELMET",
        Material.LEATHER_HELMET, Color.BLUE, "&9永冻头盔", CRYOGENIC_SUIT_LORE.toArray(new String[0]));
    public static final SlimefunItemStack CRYO_CHESTPLATE = new SlimefunItemStack("WF_CRYO_CHESTP",
        Material.LEATHER_CHESTPLATE, Color.BLUE, "&9永冻胸甲", CRYOGENIC_SUIT_LORE.toArray(new String[0]));
    public static final SlimefunItemStack CRYO_LEGGINGS = new SlimefunItemStack("WF_CRYO_LEGGINGS",
        Material.LEATHER_LEGGINGS, Color.BLUE, "&9永冻护腿", CRYOGENIC_SUIT_LORE.toArray(new String[0]));
    public static final SlimefunItemStack CRYO_BOOTS = new SlimefunItemStack("WF_CRYO_BOOTS", Material.LEATHER_BOOTS,
        Color.BLUE, "&9永冻靴子", CRYOGENIC_SUIT_LORE.toArray(new String[0]));

    // ---- Resources ----

    public static final SlimefunItemStack PYROL = SFrameTheme.sfStackFromTheme(
            "WF_PYROL",
            Material.QUARTZ,
            RESOURCES_THEME,
            "炎晶",
            "来源：末地石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&2普通"
    );

    public static final SlimefunItemStack TRAVORIDE = SFrameTheme.sfStackFromTheme(
            "WF_TRAVORIDE",
            Material.LAPIS_LAZULI,
            RESOURCES_THEME.withNameColor(ChatColor.BLUE),
            "铁镍矿",
            "来源：" + ChatColor.GRAY + "石头",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&2普通"
    );

    public static final SlimefunItemStack ADRAMALIUM = SFrameTheme.sfStackFromTheme(
            "WF_ADRAMALIUM",
            Material.NETHER_BRICK,
            RESOURCES_THEME.withNameColor(ChatColor.RED),
            "阿德拉玛金属",
            "来源：" + ChatColor.RED + "下界岩",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&2普通"
    );

    public static final SlimefunItemStack FERROS = SFrameTheme.sfStackFromTheme(
            "WF_FERROS",
            Material.RAW_IRON,
            RESOURCES_THEME.withNameColor(ChatColor.GRAY),
            "铁岩",
            "来源：深板岩，" + ChatColor.GRAY + "铁矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&1罕见"
    );

    public static final SlimefunItemStack VENEROL = SFrameTheme.sfStackFromTheme(
            "WF_VENEROL",
            Material.BLUE_DYE,
            RESOURCES_THEME.withNameColor(ChatColor.BLUE),
            "启明矿石",
            "来源：深板岩，" + ChatColor.BLUE + "青金石矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&1罕见"
    );

    public static final SlimefunItemStack NAMALON = SFrameTheme.sfStackFromTheme(
            "WF_NAMALON",
            Material.BROWN_DYE,
            RESOURCES_THEME.withNameColor(ChatColor.DARK_RED),
            "纳莫原石",
            "来源：" + ChatColor.BOLD + "&f下界石英矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&1罕见"
    );

    public static final SlimefunItemStack AURON = SFrameTheme.sfStackFromTheme(
            "WF_AURON",
            Material.GOLD_NUGGET,
            RESOURCES_THEME.withNameColor(ChatColor.YELLOW),
            "金辉",
            "来源：深板岩，" + ChatColor.YELLOW + "金矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&d稀有"
    );

    public static final SlimefunItemStack HESPERON = SFrameTheme.sfStackFromTheme(
            "WF_HESPERON",
            Material.AMETHYST_SHARD,
            RESOURCES_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "长庚矿石",
            "来源：深板岩，" + ChatColor.RED + "红石矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&d稀有"
    );

    public static final SlimefunItemStack THAUMICA = SFrameTheme.sfStackFromTheme(
            "WF_THAUMICA",
            Material.RAW_GOLD,
            RESOURCES_THEME.withNameColor(ChatColor.YELLOW),
            "萨莫感染石",
            "来源：" + ChatColor.YELLOW + "下界金矿石",
            "",
            ChatColor.of("#dff5e5") + "稀有度：&d稀有"
    );

    public static final SlimefunItemStack CRYOTIC = SFrameTheme.sfStackFromTheme(
            "WF_CRYOTIC",
            Material.DIAMOND,
            RESOURCES_THEME.withNameColor(ChatColor.BLUE),
            "永冻晶矿",
            "可在极寒之地找到。",
            Colors.ORANGE + "警告：" + RESOURCES_THEME.getLoreColor() + "立即冰冻",
            "",
            "&8⇨ &4需要永冻套装！"
    );

    public static final SlimefunItemStack CUBIC_DIODES = SFrameTheme.sfStackFromTheme(
            "WF_CUBIC_DIODES",
            HeadTextures.CUBIC_DIODES,
            RESOURCES_THEME,
            "立方二极管",
            "用以产生 Corpus 量子网络的重要元件。"
    );

    public static final SlimefunItemStack PLASTIDS = SFrameTheme.sfStackFromTheme(
            "WF_PLASTIDS",
            XMaterial.PITCHER_POD.parseMaterial() == null ? Material.BROWN_DYE : XMaterial.PITCHER_POD.parseMaterial(),
            RESOURCES_THEME,
            Colors.BROWN + "生物质",
            "布满纳米机器人的恶心组织肿块。"
    );

    public static final SlimefunItemStack RUBEDO = SFrameTheme.sfStackFromTheme(
            "WF_RUBEDO",
            enchantedItem(Material.REDSTONE),
            RESOURCES_THEME.withNameColor(ChatColor.RED),
            "红化结晶",
            "锯齿状的晶石，散发着辐射能。",
            LoreBuilder.radioactive(Radioactivity.MODERATE)
    );

    public static final SlimefunItemStack ARGON_CRYSTAL = SFrameTheme.sfStackFromTheme(
            "WF_ARGON_CRYSTAL",
            Material.END_CRYSTAL,
            RESOURCES_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "氩结晶",
            "出自虚空的放射性资源。"
    );

    public static final SlimefunItemStack CONTROL_MODULE = SFrameTheme.sfStackFromTheme(
            "WF_CONTROL_MODULE",
            Material.DETECTOR_RAIL,
            RESOURCES_THEME,
            "控制模块",
            "机器人的自主处理器。Corpus 设计。"
    );

    public static final SlimefunItemStack GALLIUM = SFrameTheme.sfStackFromTheme(
            "WF_GALLIUM",
            Material.IRON_INGOT,
            RESOURCES_THEME,
            "镓",
            "用于微电子和能量武器的软金属。",
            "通过回收高级电路板获得。"
    );

    public static final SlimefunItemStack MORPHICS = SFrameTheme.sfStackFromTheme(
            "WF_MORPHICS",
            Material.BONE_MEAL,
            RESOURCES_THEME,
            "非晶态合金",
            "一种非晶态的固体；",
            "可能是 Orokin 科技。"
    );

    public static final SlimefunItemStack NEURAL_SENSORS = SFrameTheme.sfStackFromTheme(
            "WF_NEURAL_SENSORS",
            Material.FERMENTED_SPIDER_EYE,
            RESOURCES_THEME,
            "神经传感器",
            "植入神经控制扩增系统的链接，",
            "由Grineer设计。"
    );

    public static final SlimefunItemStack NEURODES = SFrameTheme.sfStackFromTheme(
            "WF_NEURODES",
            Material.SPIDER_EYE,
            RESOURCES_THEME,
            "神经元",
            "从 Infested 身上收集的生物感应器官。"
    );

    public static final SlimefunItemStack OROKIN_CELL = SFrameTheme.sfStackFromTheme(
            "WF_OROKIN_CELL",
            Material.TURTLE_EGG,
            RESOURCES_THEME,
            "Orokin 电池",
            "Orokin 时代流传下来的远古能量包。"
    );

    public static final SlimefunItemStack TELLURIUM_FRAGMENT = SFrameTheme.sfStackFromTheme(
            "WF_TELLURIUM_FRAGMENT",
            new CustomItemStack(Material.DISC_FRAGMENT_5, (meta) -> meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)),
            RESOURCES_THEME.withNameColor(ChatColor.LIGHT_PURPLE),
            "碲碎片",
            "一个合成的碲碎片。"
    );

    public static final SlimefunItemStack TELLURIUM = SFrameTheme.sfStackFromTheme(
            "WF_TELLURIUM",
            Material.ECHO_SHARD,
            RESOURCES_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "碲",
            "这种罕见的金属来自始源星系之外。",
            "可以人工合成。"
    );

    public static final SlimefunItemStack BOOSTED_TELLURIUM = SFrameTheme.sfStackFromTheme(
            "WF_BOOSTED_TELLURIUM",
            enchantedItem(Material.ECHO_SHARD),
            RESOURCES_THEME.withNameColor(Colors.BLUE_VIOLET),
            "增强碲",
            "增强过的碲，",
            "可以用来合成某些机器。",
            "",
            LoreBuilder.radioactive(Radioactivity.VERY_DEADLY)
    );

    public static final SlimefunItemStack COOLANT_CANISTER = SFrameTheme.sfStackFromTheme(
            "WF_COOLANT_CANISTER",
            createPotion(PotionType.NIGHT_VISION),
            RESOURCES_THEME,
            "冷却液罐",
            "用于封闭热美亚裂缝。",
            "来源：" + ChatColor.BLUE + "冷却蛛形机"
    );

    public static final SlimefunItemStack DILUTED_THERMIA = SFrameTheme.sfStackFromTheme(
            "WF_DILUTED_THERMIA",
            createLingeringPotion(PotionType.FIRE_RESISTANCE),
            RESOURCES_THEME.withNameColor(ChatColor.YELLOW),
            "稀释的热美亚",
            "一种类似岩浆的液体, 在萃取过程中被冷却剂稀释。"
    );

    public static final SlimefunItemStack SALVAGE = SFrameTheme.sfStackFromTheme(
            "WF_SALVAGE",
            Material.PRISMARINE_CRYSTALS,
            RESOURCES_THEME,
            "回收金属",
            "从战后残骸中回收的高价金属。"
    );

    public static final SlimefunItemStack PRISMATIC_ENERGIZED_CORE = SFrameTheme.sfStackFromTheme(
            "WF_PRISMATIC_ENERGIZED_CORE",
            Material.DIAMOND_BLOCK,
            RESOURCES_THEME,
            "充能棱镜核心",
            "充能棱镜核心是一个独特的能量矩阵，",
            "注入了虚空与天界的能量。"
    );

    public static final SlimefunItemStack CONDENSED_PLATE = SFrameTheme.sfStackFromTheme(
            "WF_CONDENSED_PLATE",
            Material.PAPER,
            RESOURCES_THEME.withNameColor(ChatColor.DARK_RED),
            "浓缩板",
            "所有板浓缩在一起"
    );

    // ---- Alloys ----

    public static final SlimefunItemStack PYROTIC_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_PYROTIC_ALLOY",
            Material.IRON_INGOT,
            RESOURCES_THEME.withNameColor(ChatColor.WHITE),
            "炎晶合金",
            "由炎晶铸造，更加强韧的金属。"
    );

    public static final SlimefunItemStack ADRAMAL_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_ADRAMAL_ALLOY",
            Material.COPPER_INGOT,
            RESOURCES_THEME.withNameColor(Colors.BROWN),
            "阿德拉玛合金",
            "用途非常广泛的，清除了孢子和污染物的合金。"
    );

    public static final SlimefunItemStack AUROXIUM_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_AUROXIUM_ALLOY",
            Material.GOLD_INGOT,
            RESOURCES_THEME.withNameColor(ChatColor.YELLOW),
            "金辉合金",
            "由金辉锻造而成的更为坚固的金属。"
    );

    public static final SlimefunItemStack FERSTEEL_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_FERSTEEL_ALLOY",
            Material.IRON_INGOT,
            RESOURCES_THEME.withNameColor(ChatColor.GRAY),
            "钢化铁岩",
            "铁岩已经被锻造成一种更坚固的金属。"
    );

    public static final SlimefunItemStack HESPAZYM_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_HESPAZYM_ALLOY",
            Material.IRON_INGOT,
            RESOURCES_THEME.withNameColor(ChatColor.WHITE),
            "长庚合金",
            "由长庚矿石形成的一种混合物。"
    );

    public static final SlimefunItemStack TRAVOCYTE_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_TRAVOCYTE_ALLOY",
            Material.IRON_INGOT,
            RESOURCES_THEME,
            "铁镍合金",
            "由铁镍矿形成的一种混合物。"
    );

    public static final SlimefunItemStack VENERDO_ALLOY = SFrameTheme.sfStackFromTheme(
            "WF_VENERDO_ALLOY",
            Material.NETHERITE_INGOT,
            RESOURCES_THEME,
            "启明合金",
            "由启明矿石形成的一种混合物。"
    );

    public static final SlimefunItemStack THAUMIC_DISTILLATE = SFrameTheme.sfStackFromTheme(
            "WF_THAUMIC_DISTILLATE",
            Material.NETHERITE_SCRAP,
            RESOURCES_THEME.withNameColor(Colors.DARK_BROWN),
            "精粹萨莫石",
            "还原成最耐用、最易加工的元素形态的萨莫感染石。"
    );

    public static final SlimefunItemStack DEVOLVED_NAMALON = SFrameTheme.sfStackFromTheme(
            "WF_DEVOLVED_NAMALON",
            Material.IRON_INGOT,
            RESOURCES_THEME.withNameColor(ChatColor.GRAY),
            "粗加工纳莫石",
            "从它的 Infested 状态回收，以恢复其价值和功用。"
    );

    // ---- Machines ----
    public static final SlimefunItemStack CRYOTIC_EXTRACTOR = SFrameTheme.sfStackFromTheme(
            "WF_CRYO_EXTRACTOR",
            Material.BEACON,
            MACHINES_THEME.withNameColor(ChatColor.DARK_BLUE),
            "永冻晶矿提取器",
            "从极寒地区提取永冻晶矿。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(512)
    );

    public static final SlimefunItemStack ADV_CRYOTIC_EXTRACTOR = SFrameTheme.sfStackFromTheme(
            "WF_CRYO_EXTRACTOR_2",
            Material.BEACON,
            MACHINES_THEME.withNameColor(ChatColor.DARK_BLUE),
            "高级永冻晶矿提取器",
            "改进版本的永冻晶矿提取器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(1024),
            Lore.speed(4)
    );

    public static final SlimefunItemStack PRIME_CRYOTIC_EXTRACTOR = SFrameTheme.sfStackFromTheme(
            "WF_CRYO_EXTRACTOR_3",
            Material.BEACON,
            MACHINES_THEME,
            ChatColor.DARK_BLUE + "永冻晶矿提取器 " + ChatColor.GOLD + "Prime",
            "永冻晶矿提取器的 Prime 版本，",
            "使用 Orokin 科技来获得更高的效率。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(2048),
            Lore.speed(20)
    );

    public static final SlimefunItemStack THERMIA_EXTRACTOR = SFrameTheme.sfStackFromTheme(
            "WF_THERMIA_EXTRACTOR",
            Material.LODESTONE,
            MACHINES_THEME.withNameColor(Colors.ORANGE),
            "热美亚提取器",
            "一个强力的提取器，",
            "可将稀释的热美亚填充至冷却液罐中。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(512)
    );

    public static final SlimefunItemStack CONCRETE_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_CONCRETE_GEN",
            Material.BRICKS,
            MACHINES_THEME,
            "混凝土生成器",
            "这台 Orokin 机器可以生成任何混凝土",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );
    
    public static final SlimefunItemStack ADV_CONCRETE_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_CONCRETE_GEN_2",
            Material.NETHER_BRICKS,
            MACHINES_THEME,
            "高级混凝土生成器",
            "改进版本的混凝土生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_CONCRETE_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_CONCRETE_GEN_3",
            Material.RED_NETHER_BRICKS,
            MACHINES_THEME,
            MACHINES_THEME.getNameColor() + "混凝土生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的混凝土生成器，",
            "专为任何需要大量混凝土的玩家打造。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    public static final SlimefunItemStack FLOWER_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_FLOWER_GEN",
            Material.DEAD_HORN_CORAL_BLOCK,
            MACHINES_THEME.withNameColor(ChatColor.GREEN),
            "花朵生成器",
            "这种机器会在上方的花盆中生成花朵。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.production(1)
    );

    public static final SlimefunItemStack ADV_FLOWER_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_FLOWER_GEN_2",
            Material.TUFF,
            MACHINES_THEME.withNameColor(ChatColor.GREEN),
            "高级花朵生成器",
            "改进版本的花朵生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.production(8)
    );

    public static final SlimefunItemStack PRIME_FLOWER_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_FLOWER_GEN_3",
            Material.MOSS_BLOCK,
            MACHINES_THEME,
            ChatColor.GREEN + "花朵生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的花朵生成器，",
            "专为任何需要大量花朵的玩家打造。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.production(32)
    );

    public static final SlimefunItemStack WOOL_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_WOOL_GEN",
            Material.WHITE_WOOL,
            MACHINES_THEME.withNameColor(ChatColor.DARK_AQUA),
            "羊毛生成器",
            "使用 Orokin 科技生产大量的羊毛。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_WOOL_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_WOOL_GEN_2",
            Material.WHITE_WOOL,
            MACHINES_THEME.withNameColor(ChatColor.DARK_AQUA),
            "高级羊毛生成器",
            "改进版本的羊毛生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_WOOL_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_WOOL_GEN_3",
            Material.WHITE_WOOL,
            MACHINES_THEME,
            ChatColor.DARK_AQUA + "羊毛生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的羊毛生成器，",
            "专为任何需要大量羊毛的玩家打造。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    public static final SlimefunItemStack AUTO_TRADER = SFrameTheme.sfStackFromTheme(
            "WF_AUTO_TRADER",
            Material.CARTOGRAPHY_TABLE,
            MACHINES_THEME.withNameColor(ChatColor.GREEN),
            "自动交易机",
            "来自虚空商人的基本知识",
            "使该机器能够通过商人契约",
            "自动进行交易。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(1024)
    );

    public static final SlimefunItemStack ARTIFICIAL_MANGROVE = SFrameTheme.sfStackFromTheme(
            "WF_ARTIFICIAL_MANGROVE",
            Material.MUD,
            MACHINES_THEME.withNameColor(Colors.BROWN),
            "人工红树林",
            "高效地生产红树林群系的资源。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_ARTIFICIAL_MANGROVE = SFrameTheme.sfStackFromTheme(
            "WF_ARTIFICIAL_MANGROVE_2",
            Material.MUDDY_MANGROVE_ROOTS,
            MACHINES_THEME.withNameColor(Colors.BROWN),
            "高级人工红树林",
            "改进版本的人工红树林。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_ARTIFICIAL_MANGROVE = SFrameTheme.sfStackFromTheme(
            "WF_ARTIFICIAL_MANGROVE_3",
            Material.PACKED_MUD,
            MACHINES_THEME,
            Colors.BROWN + "人工红树林 " + ChatColor.GOLD + "Prime",
            "尖端版本的人工红树林。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    public static final SlimefunItemStack BASALT_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_BASALT_GEN",
            Material.BASALT,
            MACHINES_THEME.withNameColor(ChatColor.DARK_GRAY),
            "玄武岩生成器",
            "生产玄武岩",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.production(1)
    );

    public static final SlimefunItemStack ADV_BASALT_GEN = SFrameTheme.sfStackFromTheme(
            "WF_BASALT_GEN_2",
            Material.POLISHED_BASALT,
            MACHINES_THEME.withNameColor(ChatColor.DARK_GRAY),
            "高级玄武岩生成器",
            "改进版本的玄武岩生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.production(8)
    );

    public static final SlimefunItemStack PRIME_BASALT_GEN = SFrameTheme.sfStackFromTheme(
            "WF_BASALT_GEN_3",
            Material.SMOOTH_BASALT,
            MACHINES_THEME,
            ChatColor.DARK_GRAY + "玄武岩生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的玄武岩生成器，",
            "专为任何需要大量玄武岩的玩家打造。",
            "",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.production(64)
    );

    public static final SlimefunItemStack CHUNK_EATER = SFrameTheme.sfStackFromTheme(
            "WF_CHUNK_EATER",
            Material.OBSIDIAN,
            MACHINES_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "区块吞噬者",
            "破坏区块内所有在机器下方的方块。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(1024),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_CHUNK_EATER = SFrameTheme.sfStackFromTheme(
            "WF_CHUNK_EATER_2",
            Material.CRYING_OBSIDIAN,
            MACHINES_THEME.withNameColor(ChatColor.DARK_PURPLE),
            "高级区块吞噬者",
            "改进版本的区块吞噬者，",
            "能收集破坏的方块。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(2048),
            Lore.speed(1)
    );

    public static final SlimefunItemStack PRIME_CHUNK_EATER = SFrameTheme.sfStackFromTheme(
            "WF_CHUNK_EATER_3",
            enchantedItem(Material.CRYING_OBSIDIAN),
            MACHINES_THEME,
            Colors.BLUE_VIOLET + "区块吞噬者 " + ChatColor.GOLD + "Prime",
            "吞噬区块内的每一层。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(4096),
            Lore.speed(16)
    );

    public static final SlimefunItemStack TREE_PEELER = SFrameTheme.sfStackFromTheme(
            "WF_TREE_PEELER",
            Material.STONECUTTER,
            MACHINES_THEME.withNameColor(Colors.BROWN),
            "去皮器",
            "可以给原木或木板去皮的机器。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_TREE_PEELER = SFrameTheme.sfStackFromTheme(
            "WF_TREE_PEELER_2",
            Material.STONECUTTER,
            MACHINES_THEME.withNameColor(Colors.BROWN),
            "高级去皮器",
            "改进版本的去皮器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_TREE_PEELER = SFrameTheme.sfStackFromTheme(
            "WF_TREE_PEELER_3",
            Material.STONECUTTER,
            MACHINES_THEME,
            Colors.BROWN + "去皮器 " + ChatColor.GOLD + "Prime",
            "尖端版本的去皮器。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    public static final SlimefunItemStack RECYCLER = SFrameTheme.sfStackFromTheme(
            "WF_RECYCLER",
            Material.PISTON,
            MACHINES_THEME,
            "回收机",
            "用于获得回收金属与镓。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(512)
    );

    public static final SlimefunItemStack TELLURIUM_FRAGMENTS_SYNTHESIZER = SFrameTheme.sfStackFromTheme(
            "WF_TELLURIUM_FRAGS_SYNTHESIZER",
            Material.NETHER_WART_BLOCK,
            MACHINES_THEME.withNameColor(ChatColor.DARK_RED),
            "碲碎片合成器",
            "合成碲碎片。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(512)
    );

    public static final SlimefunItemStack SULFATE_PRODUCER = SFrameTheme.sfStackFromTheme(
            "WF_SULFATE_PRODUCER",
            Material.FURNACE,
            MACHINES_THEME.withNameColor(ChatColor.YELLOW),
            "硫酸盐生成器",
            "从玄武岩中提取硫酸盐。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_SULFATE_PRODUCER = SFrameTheme.sfStackFromTheme(
            "WF_SULFATE_PRODUCER_2",
            Material.FURNACE,
            MACHINES_THEME.withNameColor(Colors.ORANGE),
            "高级硫酸盐生成器",
            "改进版本的硫酸盐生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5),
            Lore.production(2)
    );

    public static final SlimefunItemStack PRIME_SULFATE_PRODUCER = SFrameTheme.sfStackFromTheme(
            "WF_SULFATE_PRODUCER_3",
            Material.FURNACE,
            MACHINES_THEME,
            ChatColor.RED + "硫酸盐生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的硫酸盐生成器。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30),
            Lore.production(8)
    );

    public static final SlimefunItemStack DUST_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_DUST_GEN",
            Material.FURNACE,
            MACHINES_THEME.withNameColor(Colors.CRAYOLA_BLUE),
            "矿粉生成器",
            "你可以选择需要生产的矿粉！",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_DUST_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_DUST_GEN_2",
            Material.FURNACE,
            MACHINES_THEME.withNameColor(Colors.CRAYOLA_BLUE),
            "高级矿粉生成器",
            "改进版本的矿粉生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(2),
            Lore.production(4)
    );

    public static final SlimefunItemStack PRIME_DUST_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_DUST_GEN_3",
            Material.FURNACE,
            MACHINES_THEME,
            Colors.CRAYOLA_BLUE + "矿粉生成器 " + ChatColor.GOLD + "Prime",
            "尖端版本的矿粉生成器。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(1024),
            Lore.speed(4),
            Lore.production(32)
    );

    public static final SlimefunItemStack GLASS_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_GLASS_GENERATOR",
            Material.GLASS,
            MACHINES_THEME.withNameColor(Colors.SAVOY_BLUE),
            "玻璃生成器",
            "一种万用的机器，",
            "可以生产各种颜色的玻璃。",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_GLASS_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_GLASS_GENERATOR_2",
            Material.LIGHT_BLUE_STAINED_GLASS,
            MACHINES_THEME.withNameColor(Colors.CRAYOLA_BLUE),
            "高级玻璃生成器",
            "改进版本的玻璃生成器。",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5),
            Lore.production(2)
    );

    public static final SlimefunItemStack PRIME_GLASS_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_GLASS_GENERATOR_3",
            Material.BLUE_STAINED_GLASS,
            MACHINES_THEME,
            Colors.NEON_BLUE + "玻璃生成器 " + ChatColor.GOLD + "Prime",
            "使用 Orokin 科技制作的",
            "尖端版玻璃生成器。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30),
            Lore.production(6)
    );

    public static final SlimefunItemStack PUTRIFIER = SFrameTheme.sfStackFromTheme(
            "WF_PUTRIFIER",
            Material.SOUL_SAND,
            MACHINES_THEME,
            Colors.BROWN + "Putrifier",
            "This machine is capable",
            "of putrefying items",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_PUTRIFIER = SFrameTheme.sfStackFromTheme(
            "WF_PUTRIFIER_2",
            Material.SOUL_SOIL,
            MACHINES_THEME,
            Colors.BROWN + "Advanced Putrifier",
            "An improved version of",
            "the Putrifier",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_PUTRIFIER = SFrameTheme.sfStackFromTheme(
            "WF_PUTRIFIER_3",
            Material.MUDDY_MANGROVE_ROOTS,
            MACHINES_THEME,
            ChatColor.GOLD + "Prime " + Colors.BROWN + "Putrifier",
            "A cutting-edge version of the",
            "Putrifier for anyone who",
            "needs a massive production",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    public static final SlimefunItemStack TERRACOTTA_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_TERRACOTTA_GEN",
            Material.LIGHT_BLUE_TERRACOTTA,
            MACHINES_THEME.withNameColor(ChatColor.BLUE),
            "Terracotta Generator",
            "A machine that can generate",
            "any non-glazed terracotta",
            "",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            Lore.powerPerSecond(128),
            Lore.speed(1)
    );

    public static final SlimefunItemStack ADV_TERRACOTTA_GEN = SFrameTheme.sfStackFromTheme(
            "WF_TERRACOTTA_GEN_2",
            Material.BLUE_TERRACOTTA,
            MACHINES_THEME.withNameColor(ChatColor.DARK_BLUE),
            "Advanced Terracotta Generator",
            "An improved version of the",
            "Terracotta Generator",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            Lore.powerPerSecond(256),
            Lore.speed(5)
    );

    public static final SlimefunItemStack PRIME_TERRACOTTA_GEN = SFrameTheme.sfStackFromTheme(
            "WF_TERRACOTTA_GEN_3",
            Material.PURPLE_TERRACOTTA,
            MACHINES_THEME,
            ChatColor.GOLD + "Prime " + ChatColor.LIGHT_PURPLE + "Terracotta Generator",
            "The best version of the",
            "Terracotta Generator",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            Lore.powerPerSecond(512),
            Lore.speed(30)
    );

    // Energy Generators
    public static final SlimefunItemStack GRAVITECH_ENERCELL = SFrameTheme.sfStackFromTheme(
            "WF_GRAVITECH_ENERCELL",
            Material.WHITE_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#8789DB")),
            "重力电池",
            "重力电池是一种运用了",
            "重力操纵技术的基本发电机。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(2000)
    );

    public static final SlimefunItemStack ARCANE_FLUX_DYNAMO = SFrameTheme.sfStackFromTheme(
            "WF_ARCANE_FLUX_DYNAMO",
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#7872D3")),
            "奥术流发电机",
            "奥术流发电机是一种利用",
            "虚空中的奥术能量进行发电的高级发电机。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(2000),
            Lore.bonusPower(1500)
    );

    public static final SlimefunItemStack SPECTRA_REACTOR = SFrameTheme.sfStackFromTheme(
            "WF_SPECTRA_REACTOR",
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#6D5DCC")),
            "光谱反应堆",
            "光谱反应堆是一种注入了光谱粒子的高效反应堆。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(4000),
            Lore.bonusPower(3000)
    );

    public static final SlimefunItemStack PRISMA_POWER_CORE = SFrameTheme.sfStackFromTheme(
            "WF_PRISMA_POWER_CORE",
            Material.CYAN_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#6548C3")),
            "棱晶能量核心",
            "棱晶能量核心是一种",
            "由稀有的棱晶合成的发电机。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(8000),
            Lore.bonusPower(6000)
    );

    public static final SlimefunItemStack VOIDLIGHT_FUSION_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_VOIDLIGHT_GEN",
            Material.BROWN_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#5F34BB")),
            "虚光聚能发电机",
            "虚光聚能发电机是能量科技的顶尖技术，",
            "利用虚空的原始能量来发电。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(16000),
            Lore.bonusPower(12000)
    );

    public static final SlimefunItemStack AXIOM_ENERGENESIS_ENGINE = SFrameTheme.sfStackFromTheme(
            "WF_AXIOM_ENGINE",
            Material.GREEN_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#4A26A6")),
            "公理能源引擎",
            "公理能源引擎是古代 Orokin 科技的一个奇迹。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(32000),
            Lore.bonusPower(24000)
    );

    public static final SlimefunItemStack CHRONOS_INFINITY_DYNAMO = SFrameTheme.sfStackFromTheme(
            "WF_CHRONOS_DYNAMO",
            Material.BLUE_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#361A90")),
            "无限时空发电机",
            "无限时空发电机利用时空异常来产生大量的能量。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(64000),
            Lore.bonusPower(48000)
    );

    public static final SlimefunItemStack PRIMORDIAL_ETERNACORE_REACTOR = SFrameTheme.sfStackFromTheme(
            "WF_PRIMORDIAL_REACTOR",
            Material.PURPLE_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#241177")),
            "原始永核反应堆",
            "原始永核反应堆利用了创世时的原始能量。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(128000),
            Lore.bonusPower(96000)
    );

    public static final SlimefunItemStack VOIDFORGE_CELESTIUM_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_VOIDFORGE_CELESTIUM_GEN",
            Material.GRAY_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.of("#16095B")),
            "虚空熔炉天界发电机",
            "虚空熔炉天界发电机代表着能量操控的顶尖水平。",
            "引导虚空与天界的能量进行发电。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(256000),
            Lore.bonusPower(192000)
    );

    public static final SlimefunItemStack ASTRAL_PRIME_GENERATOR = SFrameTheme.sfStackFromTheme(
            "WF_ASTRAL_PRIME_GEN",
            Material.BLACK_GLAZED_TERRACOTTA,
            GENERATORS_THEME.withNameColor(ChatColor.GOLD),
            ChatColor.BOLD + "星辰发电机 Prime",
            "星辰发电机 Prime 是星际战甲顶尖能源科技的缩影。",
            "以天体为燃料，并使用 Prime 技术增强输出，",
            "为无与伦比的能源生产设定了标准。",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
            Lore.powerPerSecond(1_000_000),
            Lore.bonusPower(1_000_000)
    );

    // ---- Multiblocks ----

    public static final SlimefunItemStack FOUNDRY = new SlimefunItemStack(
            "WF_FOUNDRY",
            Material.ANVIL,
            "&b铸造厂",
            "",
            "&f用于制作粘液战甲物品的特殊铸造厂。"
    );

    // ---- Relics ----

    public static final RelicItemStack LITH_A1 = new RelicItemStack(
            "古纪 A1",
            Relic.Era.LITH,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_BASALT_GEN), SlimefunItems.BLISTERING_INGOT, SlimefunItems.PLUTONIUM},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_WOOL_GENERATOR), SlimefunItems.SOLAR_GENERATOR_3},
            PrimeComponents.createCoreModule(PRIME_ARTIFICIAL_MANGROVE)
    );

    public static final RelicItemStack MESO_B1 = new RelicItemStack(
            "前纪 B1",
            Relic.Era.MESO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_CRYOTIC_EXTRACTOR), SlimefunItems.REINFORCED_ALLOY_INGOT, SFrameStacks.PYROTIC_ALLOY},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(ASTRAL_PRIME_GENERATOR), PrimeComponents.createPowerCell(PRIME_GLASS_GENERATOR)},
            PrimeComponents.createCoreModule(PRIME_BASALT_GEN)
    );

    public static final RelicItemStack NEO_C1 = new RelicItemStack(
            "中纪 C1",
            Relic.Era.NEO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_FLOWER_GENERATOR), SlimefunItems.BOOSTED_URANIUM, SFrameStacks.PLASTIDS},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_ARTIFICIAL_MANGROVE), UtilsComponents.createTemporal(PRIME_OROKIN_WAND)},
            PrimeComponents.createCoreModule(PRIME_CRYOTIC_EXTRACTOR)
    );

    public static final RelicItemStack AXI_F1 = new RelicItemStack(
            "后纪 F1",
            Relic.Era.AXI,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_TREE_PEELER), UtilsComponents.createVoidShard(PRIME_OROKIN_WAND), SlimefunItems.POWER_CRYSTAL},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_BASALT_GEN), SFrameStacks.DILUTED_THERMIA},
            PrimeComponents.createCoreModule(PRIME_FLOWER_GENERATOR)
    );

    public static final RelicItemStack LITH_T1 = new RelicItemStack(
            "古纪 T1",
            Relic.Era.LITH,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_WOOL_GENERATOR), SFrameStacks.AURON, SlimefunItems.VITAMINS},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_CRYOTIC_EXTRACTOR), SFrameStacks.ARGON_CRYSTAL},
            PrimeComponents.createCoreModule(PRIME_TREE_PEELER)
    );

    public static final RelicItemStack MESO_W1 = new RelicItemStack(
            "前纪 W1",
            Relic.Era.MESO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(ASTRAL_PRIME_GENERATOR), SlimefunItems.HARDENED_METAL_INGOT, SFrameStacks.SALVAGE},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_FLOWER_GENERATOR), UtilsComponents.createTemporal(PRIME_NOSAM_PICK)},
            PrimeComponents.createCoreModule(PRIME_WOOL_GENERATOR)
    );

    public static final RelicItemStack NEO_A1 = new RelicItemStack(
            "中纪 A1",
            Relic.Era.NEO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_ARTIFICIAL_MANGROVE), UtilsComponents.createVoidShard(PRIME_NOSAM_PICK), SlimefunItems.CARBONADO},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_TREE_PEELER), SlimefunItems.BIG_CAPACITOR},
            PrimeComponents.createCoreModule(ASTRAL_PRIME_GENERATOR)
    );

    public static final RelicItemStack AXI_N1 = new RelicItemStack(
            "后纪 N1",
            Relic.Era.AXI,
            new SlimefunItemStack[]{SlimefunItems.BLANK_RUNE, SlimefunItems.HOLOGRAM_PROJECTOR, SFrameStacks.SALVAGE},
            new SlimefunItemStack[]{SFrameStacks.TELLURIUM_FRAGMENT, SlimefunItems.FLUID_PUMP},
            UtilsComponents.createNeuralNexus(PRIME_NOSAM_PICK)
    );

    public static final RelicItemStack LITH_O1 = new RelicItemStack(
            "古纪 O1",
            Relic.Era.LITH,
            new SlimefunItemStack[]{SlimefunItems.TRASH_CAN, PrimeComponents.createControlUnit(PRIME_DUST_GENERATOR), SFrameStacks.PLASTIDS},
            new SlimefunItemStack[]{SlimefunItems.SOULBOUND_RUNE, SlimefunItems.ELECTRIC_DUST_WASHER_2},
            UtilsComponents.createNeuralNexus(PRIME_OROKIN_WAND)
    );

    public static final RelicItemStack MESO_S1 = new RelicItemStack(
            "前纪 S1",
            Relic.Era.MESO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_BASALT_GEN), SlimefunItems.SMALL_CAPACITOR, SlimefunItems.CARBONADO},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_DUST_GENERATOR), SlimefunItems.RAINBOW_RUNE},
            PrimeComponents.createCoreModule(PRIME_SULFATE_PRODUCER)
    );

    public static final RelicItemStack NEO_D1 = new RelicItemStack(
            "中纪 D1",
            Relic.Era.NEO,
            new SlimefunItemStack[]{SlimefunItems.WITHER_PROOF_OBSIDIAN, SlimefunItems.ELECTRIC_MOTOR, SlimefunItems.CARBON},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_WOOL_GENERATOR), PrimeComponents.createPowerCell(PRIME_CHUNK_EATER)},
            PrimeComponents.createCoreModule(PRIME_DUST_GENERATOR)
    );

    public static final RelicItemStack AXI_G1 = new RelicItemStack(
            "后纪 G1",
            Relic.Era.AXI,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_CHUNK_EATER), SlimefunItems.URANIUM, SlimefunItems.GOLD_24K},
            new SlimefunItemStack[]{SlimefunItems.ELECTRIC_DUST_WASHER_2, SlimefunItems.MEDIUM_CAPACITOR},
            PrimeComponents.createCoreModule(PRIME_GLASS_GENERATOR)
    );

    public static final RelicItemStack LITH_C1 = new RelicItemStack(
            "古纪 C1",
            Relic.Era.LITH,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_TREE_PEELER), PrimeComponents.createControlUnit(PRIME_GLASS_GENERATOR), SlimefunItems.REINFORCED_PLATE},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_FLOWER_GENERATOR), SlimefunItems.STAFF_ELEMENTAL},
            PrimeComponents.createCoreModule(PRIME_CHUNK_EATER)
    );

    public static final RelicItemStack MESO_C1 = new RelicItemStack(
            "Meso C1",
            Relic.Era.MESO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_CRYOTIC_EXTRACTOR), PrimeComponents.createControlUnit(PRIME_PUTRIFIER), PrimeComponents.createControlUnit(PRIME_TERRACOTTA_GEN)},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_SULFATE_PRODUCER), SlimefunItems.ARMOR_AUTO_CRAFTER},
            PrimeComponents.createCoreModule(PRIME_CONCRETE_GENERATOR)
    );

    public static final RelicItemStack NEO_P1 = new RelicItemStack(
            "Neo P1",
            Relic.Era.NEO,
            new SlimefunItemStack[]{PrimeComponents.createControlUnit(PRIME_CONCRETE_GENERATOR), PrimeComponents.createControlUnit(PRIME_SULFATE_PRODUCER), SlimefunItems.CHARGING_BENCH},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_TERRACOTTA_GEN), SlimefunItems.LAVA_GENERATOR_2},
            PrimeComponents.createCoreModule(PRIME_PUTRIFIER)
    );

    public static final RelicItemStack AXI_T1 = new RelicItemStack(
            "Axi T1",
            Relic.Era.AXI,
            new SlimefunItemStack[]{UtilsComponents.createVoidShard(PRIME_NOSAM_PICK), PrimeComponents.createControlUnit(ASTRAL_PRIME_GENERATOR), SlimefunItems.EARTH_RUNE},
            new SlimefunItemStack[]{PrimeComponents.createPowerCell(PRIME_PUTRIFIER), PrimeComponents.createPowerCell(PRIME_CONCRETE_GENERATOR)},
            PrimeComponents.createCoreModule(PRIME_TERRACOTTA_GEN)
    );

    static {
        for (Field declaredField: SFrameStacks.class.getDeclaredFields()) {
            try {
                if (declaredField.getType() != RelicItemStack.class) continue;
                RelicItemStack relicItemStack = (RelicItemStack) declaredField.get(null);
                switch (relicItemStack.getRelicEra()) {
                    case LITH -> RANDOM_LITH_RELICS.add(relicItemStack);
                    case MESO -> RANDOM_MESO_RELICS.add(relicItemStack);
                    case NEO -> RANDOM_NEO_RELICS.add(relicItemStack);
                    default -> RANDOM_AXI_RELICS.add(relicItemStack);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}