package io.github.ageuxo.chonkyreactors.util;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import net.minecraft.resources.ResourceLocation;

public class ModUtils {
    public static ResourceLocation modRL(String path){
        return new ResourceLocation(ChonkyReactors.MODID, path);
    }
}
