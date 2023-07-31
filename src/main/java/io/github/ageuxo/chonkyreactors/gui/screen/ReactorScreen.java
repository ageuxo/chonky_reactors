package io.github.ageuxo.chonkyreactors.gui.screen;

import io.github.ageuxo.chonkyreactors.gui.menu.ReactorMenu;
import io.github.ageuxo.chonkyreactors.gui.widgets.EnergyBarWidget;
import io.github.ageuxo.chonkyreactors.gui.widgets.FluidBarWidget;
import io.github.ageuxo.chonkyreactors.gui.widgets.PlayerInventoryWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ReactorScreen extends AbstractMachineScreen<ReactorMenu>{
    private EnergyBarWidget energyBarWidget;
    private FluidBarWidget fluidBarWidget;

    public ReactorScreen(ReactorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableOnly(new PlayerInventoryWidget(leftPos, topPos+80));
        energyBarWidget = this.addRenderableWidget(new EnergyBarWidget(leftPos - 40, topPos));
        fluidBarWidget = this.addRenderableWidget(new FluidBarWidget(leftPos + 40, topPos, menu.getSourceFluidTank()));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        energyBarWidget.updateWidget(menu.getEnergy(), menu.getEnergyCapacity());
        fluidBarWidget.updateWidget(menu.getFluidAmount(), menu.getFluidCapacity());
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }
}
