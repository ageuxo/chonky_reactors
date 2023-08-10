package io.github.ageuxo.chonkyreactors;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import io.github.ageuxo.chonkyreactors.block.entity.ModBlockEntities;
import io.github.ageuxo.chonkyreactors.gui.menu.ModMenuTypes;
import io.github.ageuxo.chonkyreactors.item.ModItems;
import io.github.ageuxo.chonkyreactors.item.crafting.ModRecipes;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockRegistry;
import io.github.ageuxo.chonkyreactors.network.PacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ChonkyReactors.MODID)
public class ChonkyReactors {
    public static final String MODID = "chonky_reactors";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChonkyReactors(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        PacketHandler.register();
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModRecipes.Types.register(modEventBus);
        MultiblockRegistry.MB_DEFS.register(modEventBus);

        modEventBus.register(ModBusEvents.class);

    }
}
