package io.github.ageuxo.chonkyreactors;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockDefinition;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
}
