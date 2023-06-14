package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import io.github.ageuxo.chonkyreactors.block.custom.AssemblyBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlockStateProvider extends BlockStateProvider {
//    private static final Logger LOGGER = LogUtils.getLogger();

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChonkyReactors.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        facingBlock(ModBlocks.ASSEMBLY_BLOCK);
        simpleBlockItem(ModBlocks.ASSEMBLY_BLOCK.get(), itemModels().getExistingFile(modLoc("block/assembly_block")));

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void logBlockWithItem(RegistryObject<Block> blockRegistryObject){
        Block block = blockRegistryObject.get();
        ResourceLocation rl = blockTexture(block);
        logBlock( (RotatedPillarBlock) block);
        simpleBlockItem(block, models().cubeColumn(rl.getPath(), rl, new ResourceLocation(rl.getNamespace(), rl.getPath() + "_top")));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject){
        simpleBlock(blockRegistryObject.get(), models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void facingBlock(RegistryObject<Block> blockRegistryObject){
        VariantBlockStateBuilder builder = getVariantBuilder(blockRegistryObject.get());
        ResourceLocation baseTexture = blockTexture(blockRegistryObject.get());
        BlockModelBuilder baseModel = models().cube(blockRegistryObject.getId().toString(),
                baseTexture.withSuffix("_bottom"),
                baseTexture.withSuffix("_top"),
                baseTexture,
                baseTexture.withSuffix("_back"),
                baseTexture.withSuffix("_left"),
                baseTexture.withSuffix("_right"));

        builder.forAllStates(state -> {
            Direction facing = state.getValue(AssemblyBlock.FACING);
            return ConfiguredModel.builder()
                    .modelFile(baseModel)
                    .rotationY((int) facing.getOpposite().toYRot())
                    .build();

        });
    }
}
