package io.github.ageuxo.chonkyreactors.util;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.antlr.runtime.misc.IntArray;

public class ModUtils {
    public static ResourceLocation modRL(String path){
        return new ResourceLocation(ChonkyReactors.MODID, path);
    }

    public static IntArray unpackColourValues(int packedColour){
        IntArray array = new IntArray();
        array.add(FastColor.ARGB32.alpha(packedColour));
        array.add(FastColor.ARGB32.red(packedColour));
        array.add(FastColor.ARGB32.green(packedColour));
        array.add(FastColor.ARGB32.blue(packedColour));
        return array;
    }

    public static void renderFluid(GuiGraphics guiGraphics, int x, int y, int width, int height, FluidStack fluidStack){
        if (!fluidStack.isEmpty()){
            Fluid fluid = fluidStack.getFluid();
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(fluid);
            int[] colourValues = unpackColourValues(clientType.getTintColor()).data;
            ResourceLocation texture = clientType.getStillTexture();
            guiGraphics.setColor(colourValues[0], colourValues[1], colourValues[2], colourValues[3]);
            guiGraphics.blit(texture, x, y, 0, 0, width, height);
        }
    }
}
