package net.steampn.primitivechests;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.steampn.primitivechests.common.blocks.advancedprimitivechest.AdvPrimitiveChestScreen;
import net.steampn.primitivechests.util.ModRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Primitivechests.MODID)
public class Primitivechests {
    public static final String MODID = "primitivechests";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Primitivechests() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);

        ModRegister.BLOCKS.register(modEventBus);
        ModRegister.ITEMS.register(modEventBus);
        ModRegister.BLOCK_ENTITIES.register(modEventBus);
        ModRegister.MENUS.register(modEventBus);
        ModRegister.TAB.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            MenuScreens.register(ModRegister.ADV_CHEST_MENU.get(), AdvPrimitiveChestScreen::new);
        }
    }
}
