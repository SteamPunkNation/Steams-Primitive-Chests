package net.steampn.primitivechests.common.blocks.advancedprimitivechest;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.steampn.primitivechests.util.ModRegister;

import static net.steampn.primitivechests.Primitivechests.MODID;

public class AdvancedPrimitiveChestBlockEntity extends RandomizableContainerBlockEntity {
    public static int slots = 54;
    protected final ContainerData data;
    protected NonNullList<ItemStack> items = NonNullList.withSize(slots, ItemStack.EMPTY);

    public AdvancedPrimitiveChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegister.ADVANCED_PRIMITIVE_CHEST_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return 0;
            }

            @Override
            public void set(int i, int i1) {

            }

            @Override
            public int getCount() {
                return 14;
            }
        };
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
        return Component.translatable("container." + MODID + ".advanced_primitive_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new AdvPrimitiveChestMenu(id, inventory, this, this.data);
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
