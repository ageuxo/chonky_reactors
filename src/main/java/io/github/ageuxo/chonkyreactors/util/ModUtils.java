package io.github.ageuxo.chonkyreactors.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class ModUtils {
    public static ResourceLocation modRL(String path){
        return new ResourceLocation(ChonkyReactors.MODID, path);
    }

    public static int[] unpackColourValues(int packedColour){
        return new int[]{ FastColor.ARGB32.alpha(packedColour),
                            FastColor.ARGB32.red(packedColour),
                            FastColor.ARGB32.green(packedColour),
                            FastColor.ARGB32.blue(packedColour)
                        };
    }

    public static void renderFluid(GuiGraphics guiGraphics, int x, int y, int width, int height, FluidStack fluidStack){
        if (!fluidStack.isEmpty()){
            Fluid fluid = fluidStack.getFluid();
            IClientFluidTypeExtensions clientType = IClientFluidTypeExtensions.of(fluid);
            int[] colourValues = unpackColourValues(clientType.getTintColor());
            ResourceLocation texture = clientType.getStillTexture();
            guiGraphics.setColor(colourValues[0], colourValues[1], colourValues[2], colourValues[3]);
            guiGraphics.blit(texture, x, y, 0, 0, width, height);
        }
    }

    public static final Codec<AABB> AABB_CODEC = RecordCodecBuilder.create(instance->instance.group(
            BlockPos.CODEC.fieldOf("pos1").forGetter(aabb -> new BlockPos((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ)),
            BlockPos.CODEC.fieldOf("pos2").forGetter(aabb -> new BlockPos((int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ))
    ).apply(instance, AABB::new));

}
