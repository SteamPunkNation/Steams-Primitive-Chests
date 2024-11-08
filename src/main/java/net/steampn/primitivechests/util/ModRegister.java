package net.steampn.primitivechests.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.steampn.primitivechests.common.blocks.advancedprimitivechest.AdvPrimitiveChestMenu;
import net.steampn.primitivechests.common.blocks.advancedprimitivechest.AdvancedPrimitiveChestBlock;
import net.steampn.primitivechests.common.blocks.advancedprimitivechest.AdvancedPrimitiveChestBlockEntity;
import net.steampn.primitivechests.common.blocks.primitivechest.PrimitiveChestBlock;
import net.steampn.primitivechests.common.blocks.primitivechest.PrimitiveChestBlockEntity;

import static net.steampn.primitivechests.Primitivechests.MODID;

public class ModRegister {
    public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    //Blocks
    public static final RegistryObject<Block> PRIMITIVE_CHEST = BLOCKS.register("primitive_chest",
            () -> new PrimitiveChestBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> ADVANCED_PRIMITIVE_CHEST = BLOCKS.register("advanced_primitive_chest",
            () -> new AdvancedPrimitiveChestBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));

    //Block Items
    public static final RegistryObject<Item> PRIMITIVE_CHEST_ITEM = ITEMS.register("primitive_chest",
            () -> new BlockItem(PRIMITIVE_CHEST.get(), new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ADVANCED_PRIMITIVE_CHEST_ITEM = ITEMS.register("advanced_primitive_chest",
            () -> new BlockItem(ADVANCED_PRIMITIVE_CHEST.get(), new Item.Properties().stacksTo(64)));

    // Block Entities
    public static final RegistryObject<BlockEntityType<PrimitiveChestBlockEntity>> PRIMITIVE_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("primitive_chest",
            () -> BlockEntityType.Builder.of(PrimitiveChestBlockEntity::new, PRIMITIVE_CHEST.get()).build(null));
    public static final RegistryObject<BlockEntityType<AdvancedPrimitiveChestBlockEntity>> ADVANCED_PRIMITIVE_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register("advanced_primitive_chest",
            () -> BlockEntityType.Builder.of(AdvancedPrimitiveChestBlockEntity::new, ADVANCED_PRIMITIVE_CHEST.get()).build(null));

    //Adv Chest Menu
    public static final RegistryObject<MenuType<AdvPrimitiveChestMenu>> ADV_CHEST_MENU = registerMenuType("adv_chest_menu", AdvPrimitiveChestMenu::new);

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory){
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    //Creative Menu
    public static final RegistryObject<CreativeModeTab> PRIMITIVE_TAB = TAB.register("primitive_chests", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID + ".blocks"))
            .icon(() -> new ItemStack(ModRegister.PRIMITIVE_CHEST.get()))
            .displayItems((params, output) ->{
                output.accept(PRIMITIVE_CHEST.get());
                output.accept(ADVANCED_PRIMITIVE_CHEST.get());
            })
            .build()
    );
}
