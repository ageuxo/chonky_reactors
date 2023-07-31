package io.github.ageuxo.chonkyreactors.gui.widgets;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class FluidBarWidget extends AbstractBarWidget {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String LOCALE_KEY = "gui.chonky_reactors.widget.bar.fluid";
    private final FluidTank tank;
    private FluidStack fluidStack;


    public FluidBarWidget(int pX, int pY, FluidTank lazyFluidHandler) {
        this(pX, pY, 25, 100, lazyFluidHandler);
    }

    public FluidBarWidget(int pX, int pY, int pWidth, int pHeight, FluidTank lazyFluidHandler) {
        this(pX, pY, pWidth, pHeight, Component.empty(), DEFAULT_OUTLINE_COLOUR, lazyFluidHandler);
    }

    public FluidBarWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, int outlineColour, FluidTank tank) {
        super(pX, pY, pWidth, pHeight, pMessage, outlineColour);
        this.tank = tank;
        this.barLocale = LOCALE_KEY;
        this.fluidStack = FluidStack.EMPTY;
    }

    @Override
    public void updateWidget(int currentValue, int maxValue) {
        if (this.fluidStack != tank.getFluid()){
            updateTooltip();
        }
        super.updateWidget(currentValue, maxValue);
    }

    @Override
    protected Component createTooltipComponent() {
        return Component.empty()
                .append(this.fluidStack.getDisplayName().plainCopy().withStyle(ChatFormatting.BOLD))
                .append("\n")
                .append(super.createTooltipComponent());
    }

    @Override
    public void renderBar(@NotNull GuiGraphics guiGraphics) {
        ModUtils.renderFluid(guiGraphics, this.getX(), this.getY(), this.width, this.height, this.fluidStack);
    }
}
