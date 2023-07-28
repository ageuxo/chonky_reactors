package io.github.ageuxo.chonkyreactors.gui.widgets;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FluidBarWidget extends AbstractBarWidget {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String LOCALE_KEY = "gui.chonky_reactors.widget.bar.fluid";
    private final LazyOptional<IFluidHandler> lazyFluidHandler;
    private final int tankIndex;
    private FluidStack fluidStack;


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
            this.fluidStack = lazyFluidHandler
                    .orElseThrow(ExceptionInInitializerError::new)
                    .getFluidInTank(tankIndex);
        } else {
            LOGGER.debug("FluidBarWidget tried accessing non-initialised fluid handler");
        }
    }

    @Override
    public void renderBar(@NotNull GuiGraphics guiGraphics) {
        ModUtils.renderFluid(guiGraphics, this.getX(), this.getY(), this.width, this.height, this.fluidStack);
    }
}
