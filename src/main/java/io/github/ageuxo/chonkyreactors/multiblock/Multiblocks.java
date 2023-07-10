package io.github.ageuxo.chonkyreactors.multiblock;

import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class Multiblocks {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(MultiblockRegistry.KEY, Multiblocks::bootstrap);

    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK = createKey("test_multiblock");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK1 = createKey("test_multiblock1");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK2 = createKey("test_multiblock2");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK3 = createKey("test_multiblock3");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK4 = createKey("test_multiblock4");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK5 = createKey("test_multiblock5");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK6 = createKey("test_multiblock6");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK7 = createKey("test_multiblock7");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK8 = createKey("test_multiblock8");
    public static final ResourceKey<MultiblockDefinition> TEST_MULTIBLOCK9 = createKey("test_multiblock9");


    public static void bootstrap(BootstapContext<MultiblockDefinition> context){
        register(context, TEST_MULTIBLOCK, new MultiblockDefinition("test_multiblock", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK1, new MultiblockDefinition("test_multiblock1", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK2, new MultiblockDefinition("test_multiblock2", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK3, new MultiblockDefinition("test_multiblock3", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK4, new MultiblockDefinition("test_multiblock4", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK5, new MultiblockDefinition("test_multiblock5", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK6, new MultiblockDefinition("test_multiblock6", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK7, new MultiblockDefinition("test_multiblock7", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK8, new MultiblockDefinition("test_multiblock7", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
        register(context, TEST_MULTIBLOCK9, new MultiblockDefinition("test_multiblock7", List.of(
                new MultiblockPart("test_part",
                        List.of(Blocks.COBBLESTONE),
                        1, 3, 3, 3, 1, 3))));
    }

    private static void register(BootstapContext<MultiblockDefinition> context, ResourceKey<MultiblockDefinition> key, MultiblockDefinition mbDef){
        context.register(key, mbDef);
    }

    private static ResourceKey<MultiblockDefinition> createKey(String name){
        return ResourceKey.create(MultiblockRegistry.KEY, ModUtils.modRL(name));
    }

}
