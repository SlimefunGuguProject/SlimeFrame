package me.voper.slimeframe.implementation.groups;

import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.HeadTextures;
import me.voper.slimeframe.utils.Keys;
import org.bukkit.Material;

public class Groups {

    private static final SlimeFrame ins = SlimeFrame.getInstance();

    // Title of each group
    public static final String RESOURCES_NAME =
        Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - 资源" + Colors.SILVER + ">";
    public static final String MACHINES_NAME =
        Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - 机器" + Colors.SILVER + ">";
    public static final String GENERATORS_NAME =
        Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - 发电机" + Colors.SILVER + ">";
    public static final String UTILS_AND_TOOLS_NAME =
        Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - 工具" + Colors.SILVER + ">";
    public static final String GEAR_NAME = Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - 装备" + Colors.SILVER +
        ">";
    public static final String PRIME_COMPONENTS_NAME =
        Colors.SILVER + "<" + Colors.CRAYOLA_BLUE + "粘液战甲 - Prime 组件" + Colors.SILVER + ">";

    public static final MasterGroup MAIN_GROUP = new MasterGroup(
            Keys.CAT_MAIN,
            HeadTextures.MAIN_GROUP,
            "§x§5§d§3§4§e§7粘§x§5§3§4§7§d§f液§x§4§3§6§3§d§2战§x§2§f§8§8§c§1甲"
    );

    public static final TutorialsGroup TUTORIALS = new TutorialsGroup(
            Keys.createKey("wf_tutorials"),
            new CustomItemStack(Material.ENCHANTED_BOOK, Colors.CRAYOLA_BLUE + "教程")
    );

    public static final MasterGroup RELICS = new MasterGroup(
            Keys.CAT_RELICS,
            MAIN_GROUP,
            new CustomItemStack(Material.ENDER_EYE, Colors.CRAYOLA_BLUE + "遗物"),
            Colors.CRAYOLA_BLUE + "遗物"
    );

    // Relics categories
    public static final ChildGroup LITH = new ChildGroup(
            Keys.CAT_LITH,
            RELICS,
            new CustomItemStack(HeadTextures.getSkull(HeadTextures.LITH_RELIC), "&f古纪")
    );

    public static final ChildGroup MESO = new ChildGroup(
            Keys.CAT_MESO,
            RELICS,
            new CustomItemStack(HeadTextures.getSkull(HeadTextures.MESO_RELIC), "&e前纪")
    );

    public static final ChildGroup NEO = new ChildGroup(
            Keys.CAT_NEO,
            RELICS,
            new CustomItemStack(HeadTextures.getSkull(HeadTextures.NEO_RELIC), "&2中纪")
    );

    public static final ChildGroup AXI = new ChildGroup(
            Keys.CAT_AXI,
            RELICS,
            new CustomItemStack(HeadTextures.getSkull(HeadTextures.AXI_RELIC), "&d后纪")
    );

    public static final ChildGroup RESOURCES = new ChildGroup(
            Keys.CAT_RESOURCES,
            MAIN_GROUP,
            new CustomItemStack(Material.COAL, Colors.CRAYOLA_BLUE + "资源")
    );

    public static final ChildGroup MULTIBLOCKS = new ChildGroup(
            Keys.CAT_MULTIBLOCKS,
            MAIN_GROUP,
            new CustomItemStack(HeadTextures.getSkull(HeadTextures.MULTIBLOCKS), Colors.CRAYOLA_BLUE + "多方块结构")
    );

    public static final ChildGroup MACHINES = new ChildGroup(
            Keys.CAT_MACHINES,
            MAIN_GROUP,
            new CustomItemStack(Material.STONECUTTER, Colors.CRAYOLA_BLUE + "机器")
    );

    public static final ChildGroup GENERATORS = new ChildGroup(
            Keys.CAT_GENERATORS,
            MAIN_GROUP,
            new CustomItemStack(Material.DAYLIGHT_DETECTOR, Colors.CRAYOLA_BLUE + "发电机")
    );

    public static final ChildGroup GEAR = new ChildGroup(
            Keys.CAT_GEAR,
            MAIN_GROUP,
            new CustomItemStack(Material.CHAINMAIL_CHESTPLATE, Colors.CRAYOLA_BLUE + "装备")
    );

    public static final ChildGroup UTILS_AND_TOOLS = new ChildGroup(
            Keys.CAT_UTILS_AND_TOOLS,
            MAIN_GROUP,
            new CustomItemStack(Material.SHEARS, Colors.CRAYOLA_BLUE + "工具")
    );

    public static final ChildGroup PRIME_COMPONENTS = new ChildGroup(
            Keys.CAT_PRIME_COMPONENTS,
            MAIN_GROUP,
            new CustomItemStack(Material.CONDUIT, Colors.CRAYOLA_BLUE + "Prime 组件")
    );

}
