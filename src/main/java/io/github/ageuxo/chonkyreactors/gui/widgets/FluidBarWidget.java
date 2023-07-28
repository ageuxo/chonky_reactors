package io.github.ageuxo.chonkyreactors.gui.widgets;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FluidBarWidget extends AbstractBarWidget {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String LOCALE_KEY = "gui.chonky_reactors.widget.bar.fluid";
    private final LazyOptional<IFluidHandler> lazyFluidHandler;
    private final int tankIndex;
    private ResourceLocation texture;


    public FluidBarWidget(int pX, int pY, LazyOptional<IFluidHandler> lazyFluidHandler, int tankIndex) {
        this(pX, pY, 25, 100, lazyFluidHandler, tankIndex);
    }

    public FluidBarWidget(int pX, int pY, int pWidth, int pHeight, LazyOptional<IFluidHandler> lazyFluidHandler, int tankIndex) {
        this(pX, pY, pWidth, pHeight, Component.empty(), DEFAULT_OUTLINE_COLOUR, lazyFluidHandler, tankIndex);
    }

    public FluidBarWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, int outlineColour, LazyOptional<IFluidHandler> lazyFluidHandler, int tankIndex) {
        super(pX, pY, pWidth, pHeight, pMessage, outlineColour);
        this.lazyFluidHandler = lazyFluidHandler;
        this.tankIndex = tankIndex;
        this.barLocale = LOCALE_KEY;
    }

    public void init(){
        if (lazyFluidHandler.isPresent()){
            var fluid = lazyFluidHandler
                    .orElseThrow(ExceptionInInitializerError::new)
                    .getFluidInTank(tankIndex).getFluid();
            this.texture = IClientFluidTypeExtensions.of(fluid).getStillTexture();
        } else {
            LOGGER.debug("FluidBarWidget tried accessing non-initialised fluid handler");
        }
    }

    @Override
    public void renderBar(@NotNull GuiGraphics guiGraphics) {
        guiGraphics.blit(texture, getX(), getY()+barOffset, 0, 0, width, height-barOffset);
    }
}
