package io.github.ageuxo.chonkyreactors.gui.menu;

import io.github.ageuxo.chonkyreactors.block.entity.ReactorBlockEntity;
import io.github.ageuxo.chonkyreactors.util.EnergyDataProvider;
import io.github.ageuxo.chonkyreactors.util.FluidDataProvider;
import io.github.ageuxo.chonkyreactors.util.VariableEnergyStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ReactorMenu extends AbstractMachineMenu implements EnergyDataProvider, FluidDataProvider {
    public final ReactorBlockEntity blockEntity;

    public ReactorMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public ReactorMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.REACTOR_MENU.get(), id, inv, entity, data, data.getCount());
        this.blockEntity = (ReactorBlockEntity) entity;
    }

    @Override
    public VariableEnergyStorage getSourceEnergy() {
        return blockEntity.getSourceEnergy();
    }

    @Override
    public FluidTank getSourceFluidTank() {
        return blockEntity.getSourceFluidTank();
    }
}
