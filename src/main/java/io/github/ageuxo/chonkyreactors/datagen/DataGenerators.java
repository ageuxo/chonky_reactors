package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ChonkyReactors.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new ModRecipeProvider(packOutput));
        generator.addProvider(true, ModLootTableProvider.create(packOutput));
        generator.addProvider(true, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(true, new ModTagsProvider.Blocks(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModWorldGenProvider(packOutput, lookupProvider));
//        generator.addProvider(event.includeServer(), MultiblockRegistry.getDataProvider(event));
        generator.addProvider(event.includeServer(), new MultiblockProvider(packOutput, lookupProvider));

    }
}
