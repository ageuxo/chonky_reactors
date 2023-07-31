package io.github.ageuxo.chonkyreactors.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBarWidget extends AbstractWidget {
    protected static final int OUTLINE_THICKNESS = 1;
    protected static final int DEFAULT_OUTLINE_COLOUR = 0xff8e8e8e;
    protected final int outlineColour;
    protected int barOffset;
    protected String barLocale;

    public AbstractBarWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage, int outlineColour) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.outlineColour = outlineColour;
    }

    public AbstractBarWidget(int pX, int pY, int pWidth, int pHeight) {
        this(pX, pY, pWidth, pHeight, Component.empty(), DEFAULT_OUTLINE_COLOUR);
    }

    public abstract void renderBar(@NotNull GuiGraphics guiGraphics);

    public abstract void init();

    public void updateWidget(int currentValue, int maxValue) {
        int scaledValue = currentValue * this.getHeight() / Math.max(maxValue, 1);
        this.barOffset = this.height - scaledValue;
        this.setTooltip(Tooltip.create(Component.translatable(this.barLocale, currentValue, maxValue)));
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.renderOutline( this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.outlineColour);
        guiGraphics.fill( this.getX()+ AbstractBarWidget.OUTLINE_THICKNESS, this.getY()+ AbstractBarWidget.OUTLINE_THICKNESS, this.getX()+this.getWidth()- AbstractBarWidget.OUTLINE_THICKNESS, this.getY()+getHeight()- AbstractBarWidget.OUTLINE_THICKNESS, 0xff202020);
        this.renderBar(guiGraphics);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }

    @Override
    protected @NotNull ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }
}
