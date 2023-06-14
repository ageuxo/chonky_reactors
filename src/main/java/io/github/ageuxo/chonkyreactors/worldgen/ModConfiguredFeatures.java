package io.github.ageuxo.chonkyreactors.worldgen;

import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> DUSTY_KEY = registerKey("dusty");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){


    }





    public static ResourceKey<ConfiguredFeature<?,?>> registerKey(String name){
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ModUtils.modRL(name));
    }
    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<? ,?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
