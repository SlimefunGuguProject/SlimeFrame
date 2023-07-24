package me.voper.slimeframe.slimefun.items.tools;

import com.jeff_media.morepersistentdatatypes.DataType;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.interfaces.InventoryBlock;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.voper.slimeframe.utils.ChatUtils;
import me.voper.slimeframe.utils.Colors;
import me.voper.slimeframe.utils.Keys;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Method;
import java.util.Optional;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class InputConfigurator extends SimpleSlimefunItem<ItemUseHandler> {

    private static final NamespacedKey ITEMSTACK_ARRAY = Keys.createKey("wf_input_config_items");
    private static final NamespacedKey MACHINE_ID = Keys.createKey("wf_input_config_machine_id");

    private static final String SIMULATION_CHAMBER_ID = "MOB_SIMULATION_CHAMBER";

    public InputConfigurator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
                    BlockMenu inventory = BlockStorage.getInventory(block);
                    if (inventory != null) {
//                        int[] slotsAccessedByItemTransport = ((DirtyChestMenu) inventory).getPreset().getSlotsAccessedByItemTransport((DirtyChestMenu) inventory, ItemTransportFlow.INSERT, null);
//                        if (item instanceof InventoryBlock || hasGetInputSlots(item)) {
                        int[] input = getInputSlots(item, inventory);
                        if (input.length != 0) {
                            if (p.isSneaking()) {
                                setConfiguration(item, e.getItem(), inventory, p, input);
                            } else {
                                applyConfiguration(item, e.getItem(), inventory, p, input);
                            }
                        } else {
                            p.sendMessage(item.getItemName() + ChatColor.RED + " 没有输入栏位！");
                        }
//                        }
                    }
                }
            }

            e.cancel();

        };
    }

    private int[] getInputSlots(SlimefunItem item, DirtyChestMenu inventory) {
        int[] slotsAccessedByItemTransport = inventory.getPreset().getSlotsAccessedByItemTransport(inventory, ItemTransportFlow.INSERT, null);
        if (slotsAccessedByItemTransport != null && slotsAccessedByItemTransport.length != 0) {
            return slotsAccessedByItemTransport;
        }

        if (item instanceof InventoryBlock inventoryBlock) {
            return inventoryBlock.getInputSlots();
        }

        if (hasGetInputSlots(item)) {
            return invokeGetInputSlots(item);
        }

        if (item.getId().equals(SIMULATION_CHAMBER_ID)) {
            return new int[]{37};
        }

        return new int[0];
    }

    private void setConfiguration(SlimefunItem sfItem, ItemStack item, BlockMenu menu, Player player, int[] input) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;

        ItemStack[] itemStacks = new ItemStack[input.length];

        for (int i = 0; i < input.length; i++) {
            ItemStack itemInSlot = menu.getItemInSlot(input[i]);
            if (itemInSlot == null || itemInSlot.getType() == Material.AIR) continue;
            itemStacks[i] = itemInSlot;
        }

        itemMeta.getPersistentDataContainer().set(ITEMSTACK_ARRAY, DataType.ITEM_STACK_ARRAY, itemStacks);
        PersistentDataAPI.set(itemMeta, MACHINE_ID, PersistentDataType.STRING, sfItem.getId());
        item.setItemMeta(itemMeta);
        ChatUtils.sendMessage(player, ChatColor.GREEN + "已设置！");
    }

    private void applyConfiguration(SlimefunItem sfItem, ItemStack item, BlockMenu menu, Player player, int[] input) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null) return;

        ItemStack[] itemsStored = itemMeta.getPersistentDataContainer().get(ITEMSTACK_ARRAY, DataType.ITEM_STACK_ARRAY);
        String machineID = PersistentDataAPI.get(itemMeta, MACHINE_ID, PersistentDataType.STRING);

        if (itemsStored == null || machineID == null) {
            player.sendMessage(Colors.ORANGE + "输入配置器没有进行设置。");
            return;
        }

        if (!machineID.equals(sfItem.getId())) {
            player.sendMessage(ChatColor.RED + "输入配置器已设置为机器：" + machineID);
            return;
        }

        for (int i = 0; i < input.length; i++) {
            ItemStack itemInSlot = menu.getItemInSlot(input[i]);
            if (itemInSlot != null && itemInSlot.getType() != Material.AIR) {
                menu.getLocation().getWorld().dropItemNaturally(menu.getLocation(), itemInSlot.clone());
                itemInSlot.setAmount(0);
            }
        }

        int i = 0;
        for (ItemStack itemToAdd: itemsStored) {
            if (itemToAdd != null && itemToAdd.getType() != Material.AIR) {
                boolean worked = false;
                for (ItemStack stack : player.getInventory()) {
                    if (ItemUtils.canStack(stack, itemToAdd)) {
                        if (stack.getAmount() >= itemToAdd.getAmount()) {
                            ItemUtils.consumeItem(stack, itemToAdd.getAmount(), false);
                            menu.replaceExistingItem(input[i], itemToAdd);
                            player.sendMessage(ItemUtils.getItemName(itemToAdd) + ChatColor.GREEN + " 已成功添加！");
                            worked = true;
                            break;
                        }
                    }
                }
                if (!worked) {
                    player.sendMessage(Colors.ORANGE + "没有足够的物品来填充输入栏位！");
                }
            }
            i++;
        }
    }

    private boolean hasGetInputSlots(SlimefunItem item) {
        Class<? extends SlimefunItem> aClass = item.getClass();
        try {
            Method method = aClass.getMethod("getInputSlots");
            return method.getReturnType().isArray() && method.getReturnType().getComponentType() == int.class;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private int[] invokeGetInputSlots(SlimefunItem item) {
        Class<? extends SlimefunItem> aClass = item.getClass();
        try {
            Method getInputSlots = aClass.getMethod("getInputSlots");
            getInputSlots.setAccessible(true);
            return (int[]) getInputSlots.invoke(item);
        } catch (Exception ignored) {}

        return new int[0];
    }

}
