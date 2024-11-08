package net.steampn.primitivechests.common.blocks.advancedprimitivechest;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.steampn.primitivechests.util.ModRegister;

public class AdvPrimitiveChestMenu extends AbstractContainerMenu {
    public final AdvancedPrimitiveChestBlockEntity blockEntity;
    private final Level level;
    private static final int CHEST_SLOTS = 14;

    public AdvPrimitiveChestMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData){
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(14));
    }

    public AdvPrimitiveChestMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModRegister.ADV_CHEST_MENU.get(), pContainerId);
        checkContainerSize(inv, CHEST_SLOTS);
        blockEntity = ((AdvancedPrimitiveChestBlockEntity) entity);
        level = inv.player.level();

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            for (int col = 0; col < 9; ++col)
                this.addSlot(new SlotItemHandler(iItemHandler, col, 8 + col * 18, 18));

            for (int col = 0; col < 5; ++col)
                this.addSlot(new SlotItemHandler(iItemHandler, col + 9, 44 + col * 18, 36));
        });

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack newSlot = slot.getItem();
            stack = newSlot.copy();
            if (pIndex < CHEST_SLOTS) {
                if (!this.moveItemStackTo(newSlot, CHEST_SLOTS, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(newSlot, 0, CHEST_SLOTS, false)) {
                return ItemStack.EMPTY;
            }

            if (newSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModRegister.ADVANCED_PRIMITIVE_CHEST.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
