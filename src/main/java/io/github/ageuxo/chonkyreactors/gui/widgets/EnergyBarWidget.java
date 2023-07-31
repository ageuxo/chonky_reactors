package io.github.ageuxo.chonkyreactors.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

public class EnergyBarWidget extends AbstractBarWidget {
    private static final int DEFAULT_FROM_GRADIENT = FastColor.ARGB32.color(255, 200, 0, 0);
    private static final int DEFAULT_TO_GRADIENT = FastColor.ARGB32.color(255, 255, 0, 0);
    private static final String LOCALE_KEY = "gui.chonky_reactors.widget.bar.energy";
    private final int gradientFrom;
    private final int gradientTo;

    public EnergyBarWidget(int pX, int pY) {
        this(pX, pY, 25, 100);
    }

    public EnergyBarWidget(int pX, int pY, int pWidth, int pHeight) {
        this(pX, pY, pWidth, pHeight, DEFAULT_OUTLINE_COLOUR, DEFAULT_FROM_GRADIENT, DEFAULT_TO_GRADIENT);
    }

    public EnergyBarWidget(int pX, int pY, int pWidth, int pHeight, int outlineColour, int gradientFrom, int gradientTo) {
        super(pX, pY, pWidth, pHeight, Component.empty(), outlineColour);
        this.gradientFrom = gradientFrom;
        this.gradientTo = gradientTo;
        this.barLocale = LOCALE_KEY;
    }

    @Override
    public void renderBar(@NotNull GuiGraphics guiGraphics) {
        guiGraphics.fillGradient( this.getX()+ OUTLINE_THICKNESS, this.getY()+ OUTLINE_THICKNESS +barOffset,
                this.getX()+this.getWidth()- OUTLINE_THICKNESS, this.getY()+this.getHeight()- OUTLINE_THICKNESS,
                gradientFrom, gradientTo);
    }

}
