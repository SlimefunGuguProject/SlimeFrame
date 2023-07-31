package me.voper.slimeframe.slimefun.items.tools;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.ChatUtils;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.Keys;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.regex.Pattern;

@ParametersAreNonnullByDefault
public class SelectorConfigurator extends SimpleSlimefunItem<ItemUseHandler> {

    private static final NamespacedKey INDEX_STORED = Keys.createKey("wf_selector_config_items");
    private static final NamespacedKey MACHINE_ID = Keys.createKey("wf_selector_config_machine_id");

    public SelectorConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            final Player p = e.getPlayer();
            final Optional<Block> clickedBlock = e.getClickedBlock();

            if (clickedBlock.isPresent()) {
                final Block block = clickedBlock.get();
                final SlimefunItem item = BlockStorage.check(block);
                if (Slimefun.getProtectionManager().hasPermission(p, block, Interaction.INTERACT_BLOCK) && item != null) {
                    if (item instanceof AbstractSelectorMachine selectorMachine) {
                        BlockMenu inventory = BlockStorage.getInventory(block);
                        if (inventory != null) {
                            if (p.isSneaking()) {
                                setConfiguration(selectorMachine, inventory, e.getItem(), p);
                            } else {
                                applyConfiguration(selectorMachine, inventory, e.getItem(), p);
                            }
                        }
                    } else {
                        ChatUtils.sendMessage(p, ChatColor.RED + "你不能对该机器使用此物品。");
                    }
                }
            }

            e.cancel();
        };
    }

    protected void setConfiguration(AbstractSelectorMachine selectorMachine, BlockMenu menu, ItemStack configurator, Player player) {
        int index = Integer.parseInt(BlockStorage.getLocationInfo(menu.getLocation(), selectorMachine.getBlockKey()));

        ItemMeta itemMeta = configurator.getItemMeta();

        String id = selectorMachine.getId();
        String regex = ".*_\\d$";

        if (Pattern.matches(regex, id)) {
            id = id.substring(0, id.length() - 2);
        }

        PersistentDataAPI.setString(itemMeta, MACHINE_ID, id);
        PersistentDataAPI.setInt(itemMeta, INDEX_STORED, index);
        configurator.setItemMeta(itemMeta);

        ChatUtils.sendMessage(player, ChatColor.GREEN + "已配置机器。");
    }

    protected void applyConfiguration(AbstractSelectorMachine selectorMachine, BlockMenu menu, ItemStack configurator, Player player) {
        ItemMeta itemMeta = configurator.getItemMeta();

        int index = PersistentDataAPI.getInt(itemMeta, INDEX_STORED);
        String id = PersistentDataAPI.getString(itemMeta, MACHINE_ID);

        if (index == -1 || id == null) {
            ChatUtils.sendMessage(player, Colors.ORANGE + "选择配置器未存储任何配置！");
            return;
        }

        if (!selectorMachine.getId().contains(id)) {
            ChatUtils.sendMessage(player, ChatColor.RED + "该配置器绑定的机器为：" + id);
            return;
        }

        selectorMachine.select(menu, index);
        ChatUtils.sendMessage(player, ChatColor.GREEN + "已应用设置！");
    }

}
