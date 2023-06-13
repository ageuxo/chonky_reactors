package io.github.ageuxo.chonkyreactors.gui.widgets;

import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PlayerInventoryWidget extends AbstractWidget {
    protected static final ResourceLocation TEXTURE = ModUtils.modRL("textures/gui/widgets/inventory.png");
    private static final Component TITLE = Component.translatable("container.inventory");

    public PlayerInventoryWidget(int pX, int pY) {
        super(pX, pY, 176, 86, TITLE);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        guiGraphics.blit(TEXTURE, this.getX(), this.getY(), 0, 0,  this.getWidth(), this.getHeight(), 176, 86);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {

    }




}
