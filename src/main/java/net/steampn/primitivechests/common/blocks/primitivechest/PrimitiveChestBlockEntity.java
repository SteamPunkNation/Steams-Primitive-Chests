package net.steampn.primitivechests.common.blocks.primitivechest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.steampn.primitivechests.util.ModRegister;

import static net.steampn.primitivechests.Primitivechests.MODID;

public class PrimitiveChestBlockEntity extends RandomizableContainerBlockEntity {

    public static int slots = 45;
    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    public PrimitiveChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegister.PRIMITIVE_CHEST_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container." + MODID + ".primitive_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
//        return new PrimitiveChestContainer(id, inventory);
        return new ChestMenu(MenuType.GENERIC_9x1, id, inventory, this, 1);
    }

    @Override
    public int getContainerSize() {
        return slots;
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if(!this.trySaveLootTable(compoundTag)){
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)){
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }
    }
}
