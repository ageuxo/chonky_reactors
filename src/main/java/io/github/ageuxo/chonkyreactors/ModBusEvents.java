package io.github.ageuxo.chonkyreactors;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.gui.menu.ModMenuTypes;
import io.github.ageuxo.chonkyreactors.gui.screen.AssemblyBlockScreen;
import io.github.ageuxo.chonkyreactors.gui.screen.ReactorScreen;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockDefinition;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;


public class ModBusEvents {
    public static final Logger LOGGER = LogUtils.getLogger();
    @Mod.EventBusSubscriber(modid = ChonkyReactors.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common{
        @SubscribeEvent
        public static void onRegisterDatapacks(DataPackRegistryEvent.NewRegistry event){
            event.dataPackRegistry(MultiblockRegistry.KEY, MultiblockDefinition.CODEC, MultiblockDefinition.CODEC);
        }
    }

    @Mod.EventBusSubscriber(modid = ChonkyReactors.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class Client {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            event.enqueueWork(
                    ()-> {
                        MenuScreens.register(ModMenuTypes.ASSEMBLY_BLOCK_MENU.get(), AssemblyBlockScreen::new);
                        MenuScreens.register(ModMenuTypes.REACTOR_MENU.get(), ReactorScreen::new);
                    }
            );
        }
    }
}
