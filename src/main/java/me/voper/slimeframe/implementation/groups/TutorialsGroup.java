package me.voper.slimeframe.implementation.groups;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.guide.SurvivalSlimefunGuide;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;

import me.voper.slimeframe.utils.Colors;

import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

@ParametersAreNonnullByDefault
public class TutorialsGroup extends FlexItemGroup {

    private static final int GUIDE_BACK = 1;

    private static final int RELICS_TUTORIAL = 9;
    private static final int GENERATORS_TUTORIAL = 10;
    private static final int SPECIAL_ORES_TUTORIAL = 11;

    private static final int[] FOOTER = new int[]{
            45, 46, 47, 48, 49, 50, 51, 52, 53
    };

    private final boolean visibility = true;

    protected TutorialsGroup(NamespacedKey key, ItemStack item) {
        super(key, item);
        Groups.MAIN_GROUP.addSubGroup(this);
    }

    @Override
    public boolean isVisible(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        return visibility;
    }

    @Override
    public void open(Player player, PlayerProfile playerProfile, SlimefunGuideMode slimefunGuideMode) {
        this.openGuide(player, playerProfile, slimefunGuideMode, 1);
    }

    private void openGuide(Player player, PlayerProfile profile, SlimefunGuideMode mode, int page) {
        GuideHistory history = profile.getGuideHistory();
        if (mode == SlimefunGuideMode.SURVIVAL_MODE) {
            history.add(this, page);
        }

        ChestMenu menu = new ChestMenu(Colors.CRAYOLA_BLUE + "粘液战甲教程");
        SurvivalSlimefunGuide guide = (SurvivalSlimefunGuide) Slimefun.getRegistry().getSlimefunGuide(mode);

        menu.setEmptySlotsClickable(false);
        menu.addMenuOpeningHandler(pl -> pl.playSound(pl.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1));
        guide.createHeader(player, profile, menu);

        for (int slot : FOOTER) {
            menu.addItem(slot, ChestMenuUtils.getBackground(), (player1, i1, itemStack, clickAction) -> false);
        }

        // Back
        menu.replaceExistingItem(GUIDE_BACK, ChestMenuUtils.getBackButton(player, Slimefun.getLocalization().getMessage("guide.back.guide")));
        menu.addMenuClickHandler(GUIDE_BACK, (player1, slot, itemStack, clickAction) -> {
            SlimefunGuide.openItemGroup(profile, Groups.MAIN_GROUP, mode, 1);
            return false;
        });

        // Relics Tutorial
        menu.replaceExistingItem(RELICS_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE +
            "遗物教程"));
        menu.addMenuClickHandler(RELICS_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getRelicsTutorial());
            return false;
        }));

        // Generators Tutorial
        menu.replaceExistingItem(GENERATORS_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE +
            "发电机教程"));
        menu.addMenuClickHandler(GENERATORS_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getGeneratorsTutorial());
            return false;
        }));
        menu.open(player);

        // Special Ores Farm
        menu.replaceExistingItem(SPECIAL_ORES_TUTORIAL, new CustomItemStack(Material.ENCHANTED_BOOK, Colors.BRONZE +
            "资源农场教程"));
        menu.addMenuClickHandler(SPECIAL_ORES_TUTORIAL, ((player1, i, itemStack, clickAction) -> {
            player1.getInventory().addItem(getSpecialOresTutorial());
            return false;
        }));
        menu.open(player);
    }

    @Nonnull
    private ItemStack getRelicsTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "遗物教程");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("遗物是干什么的？\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("遗物是用来获得 Prime 组件，以用合成 Prime 物品的。" +
                    "每个遗物拥有3个铜奖励，2个银奖励，与1个金奖励。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("如何获得遗物？\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("查看遗物分类了解详情。总体来说，一共有4种类型的遗物：古纪，前纪，中纪，后纪。" +
                    "通过钓鱼来获得古纪遗物，击杀生物来获得前纪遗物，破坏方块来获得中纪遗物，放置方块来获得后纪遗物。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("如何开启遗物？\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("副手手持遗物，并击杀末影人，直到反应物数量达到10。" +
                    "在这之后，手持遗物右键点击即可获得奖励。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("基岩版玩家\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("如果你是基岩版玩家，遗物必须在你快捷栏的第一格，才能积攒反应物。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("虚空光体\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("虚空光体可以用来精炼遗物，增加稀有物品掉落几率。" +
                    "每次开启遗物后，你可以获得虚空光体。使用指令/sframe traces 来查询虚空光体数量。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("如何精炼遗物？\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("要精炼遗物，使用指令 /sframe refine <精炼等级（英文）>。精炼等级的提升会增加获取稀有物品的几率。").underlined(false).color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("精炼等级\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("EXCEPTIONAL（优良）：需要25虚空光体\n\nFLAWLESS（无暇）：需要50虚空光体\n\n" +
                    "RADIANT（光辉）：需要100虚空光体").underlined(false).color(ChatColor.BLACK)
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

    @Nonnull
    private ItemStack getGeneratorsTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "发电机教程");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("叠加式发电机\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("部分粘液战甲的发电机拥有额外的属性：").color(ChatColor.BLACK).underlined(false)
                .append("额外能量").bold(true)
                .create());

        components.add(new ComponentBuilder("额外能量\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("发电机会检测其6个方向的相邻方块是否为叠加式发电机。").color(ChatColor.BLACK).underlined(false)
                .append("每个叠加式发电机会增加一次额外能量到总发电量中。")
                .create());

        components.add(new ComponentBuilder("示例\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("如果一个发电机基础发电 2,000 J，额外能量为 1,000 J，" +
                    "当6个方向的发电机都是叠加式发电机时，" +
                    "总共产出的能量为 8,000J（6 * 1,000 + 2,000）。").color(ChatColor.BLACK).underlined(false)
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

    @Nonnull
    private ItemStack getSpecialOresTutorial() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta itemMeta = (BookMeta) book.getItemMeta();
        itemMeta.setAuthor("Voper");
        itemMeta.setTitle(ChatColor.BLUE + "新资源教程");

        ArrayList<BaseComponent[]> components = new ArrayList<>();
        components.add(new ComponentBuilder("新的矿石\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("你可以在资源页面中找到新的矿石，可通过诺萨姆切割器挖掘。").color(ChatColor.BLACK).underlined(false)
                .append("当前，只有一种方式能自动化获取这些资源。")
                .create());

        components.add(new ComponentBuilder("自动交易机\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("你需要新的机器：").color(ChatColor.BLACK).underlined(false)
                .append("自动交易机").bold(true)
                .append("来进行矿石资源自动化。该机器可自动与村民进行交易。").bold(false)
                .create());

        components.add(new ComponentBuilder("契约\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("为了使自动交易机工作，你需要放入商人灵魂契约。").color(ChatColor.BLACK).underlined(false)
                .create());

        components.add(new ComponentBuilder("与村民交易\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("当你拥有契约后，您需要找到一位").color(ChatColor.BLACK).underlined(false)
                .append("提供新资源交易的村民。")
                .append("概率很低，但与村民的职业无关。任何村民都有可能提供。")
                .create());

        components.add(new ComponentBuilder("最后一步\n\n").color(Colors.CRAYOLA_BLUE).underlined(true)
                .append("当你找到符合条件的村民后，").color(ChatColor.BLACK).underlined(false)
                .append("手持商人灵魂契约，并右键点击村民来激活。")
                .append("注意：").color(ChatColor.DARK_RED)
                .append("这会杀死村民！").color(ChatColor.BLACK)
                .create());

        components.add(new ComponentBuilder("当拥有了合适的契约后，")
                .append("放入自动交易机，并提供足够的资源即可。")
                .create());

        itemMeta.spigot().setPages(components);
        book.setItemMeta(itemMeta);
        return book;
    }

}