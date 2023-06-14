package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import io.github.ageuxo.chonkyreactors.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModTagsProvider {

    public static class Blocks extends BlockTagsProvider{

        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, ChonkyReactors.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            this.tag(ModTags.MACHINES).add(ModBlocks.ASSEMBLY_BLOCK.get());

        }
    }


    public static class Items extends ItemTagsProvider{

        public Items(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> lookupCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
            super(packOutput, providerCompletableFuture, lookupCompletableFuture, ChonkyReactors.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
//            this.tag()
        }
    }
}
