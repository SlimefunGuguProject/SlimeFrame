package me.voper.slimeframe.slimefun.items.relics;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import lombok.Getter;
import lombok.Setter;
import me.voper.slimeframe.slimefun.utils.HeadTextures;
import me.voper.slimeframe.utils.Colors;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ParametersAreNonnullByDefault
public class RelicItemStack extends SlimefunItemStack {

    private final Relic.Era relicEra;
    private final SlimefunItemStack[] commonDrops;
    private final SlimefunItemStack[] uncommonDrops;
    private final SlimefunItemStack rareDrop;

    public RelicItemStack(ItemStack item, String name, Relic.Era era, SlimefunItemStack[] commonDrops, SlimefunItemStack[] uncommonDrops, SlimefunItemStack rareDrop) {
        super("WF_" + era.name() + "_" + ChatColor.stripColor(name).split(" ")[1], item, (itemMeta -> {
            ChatColor nameColor;
            switch (era) {
                case LITH -> nameColor = ChatColor.WHITE;
                case MESO -> nameColor = ChatColor.YELLOW;
                case NEO -> nameColor = ChatColor.GREEN;
                default -> nameColor = ChatColor.LIGHT_PURPLE;
            }

            itemMeta.setDisplayName(nameColor + name);
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.AQUA + "反应物：" + ChatColor.WHITE + "0/10");
            lore.add(ChatColor.AQUA + "精炼：" + ChatColor.WHITE + Relic.Refinement.INTACT.name());
            lore.add("");
            lore.add(Colors.BRONZE + String.valueOf(ChatColor.BOLD) + "普通奖励：");
            for (SlimefunItemStack commonDrop: commonDrops) {
                lore.add(ChatColor.WHITE + commonDrop.getDisplayName());
            }
            lore.add("");
            lore.add(Colors.SILVER + String.valueOf(ChatColor.BOLD) + "罕见奖励：");
            for (SlimefunItemStack uncommonDrop: uncommonDrops) {
                lore.add(ChatColor.WHITE + uncommonDrop.getDisplayName());
            }
            lore.add("");
            lore.add(Colors.GOLD_2 + String.valueOf(ChatColor.BOLD) + "稀有奖励：");
            lore.add(ChatColor.WHITE + rareDrop.getDisplayName());
            itemMeta.setLore(lore);
        }));

        this.relicEra = era;
        this.commonDrops = commonDrops;
        this.uncommonDrops = uncommonDrops;
        this.rareDrop = rareDrop;
    }

    public RelicItemStack(String name, Relic.Era era, SlimefunItemStack[] commonDrops, SlimefunItemStack[] uncommonDrops, SlimefunItemStack rareDrop) {
        this(getTextureByEra(era), name, era, commonDrops, uncommonDrops, rareDrop);
    }

    private static ItemStack getTextureByEra(Relic.Era era) {
        ItemStack skull;
        switch (era) {
            case LITH -> skull = HeadTextures.getSkull(HeadTextures.LITH_RELIC);
            case MESO -> skull = HeadTextures.getSkull(HeadTextures.MESO_RELIC);
            case NEO -> skull = HeadTextures.getSkull(HeadTextures.NEO_RELIC);
            default -> skull = HeadTextures.getSkull(HeadTextures.AXI_RELIC);
        }
        return skull;
    }

}
