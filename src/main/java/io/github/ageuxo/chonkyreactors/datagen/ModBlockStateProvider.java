package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import io.github.ageuxo.chonkyreactors.block.custom.AssemblyBlock;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
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
        facingBlock(ModBlocks.ASSEMBLY_BLOCK, "machine/assembly");
        simpleBlockItem(ModBlocks.ASSEMBLY_BLOCK.get(), itemModels().getExistingFile(modLoc("block/assembly_block")));

        oreBlockWithItem(ModBlocks.PHLOGITE_ORE_BLOCK);

    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void oreBlockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), models()
                .withExistingParent(blockRegistryObject.getId().getPath(), ModUtils.modRL("block/ore_template"))
                .texture("overlay", ModUtils.modRL("block/ore/"+ blockRegistryObject.getId().getPath()) ));
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

    private void facingBlock(RegistryObject<Block> blockRegistryObject, String blockFolder){
        VariantBlockStateBuilder builder = getVariantBuilder(blockRegistryObject.get());
        ResourceLocation baseTexture = ModUtils.modRL("block/"+blockFolder+"/"+blockRegistryObject.getId().getPath());
        BlockModelBuilder baseModel = models().cube(blockRegistryObject.getId().toString(),
                baseTexture.withSuffix("_bottom"),
                baseTexture.withSuffix("_top"),
                baseTexture,
                baseTexture.withSuffix("_back"),
                baseTexture.withSuffix("_left"),
                baseTexture.withSuffix("_right"))
                .texture("particle", baseTexture);

        builder.forAllStates(state -> {
            Direction facing = state.getValue(AssemblyBlock.FACING);
            return ConfiguredModel.builder()
                    .modelFile(baseModel)
                    .rotationY((int) facing.getOpposite().toYRot())
                    .build();

        });
    }
}
