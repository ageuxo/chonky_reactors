package io.github.ageuxo.chonkyreactors.block.entity;

import io.github.ageuxo.chonkyreactors.ReactorTier;
import io.github.ageuxo.chonkyreactors.util.VariableEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ReactorBlockEntity extends BlockEntity implements MenuProvider {
    private ReactorTier reactorTier;
    private VariableEnergyStorage energyStorage = new VariableEnergyStorage(8000, 0, 64, 8000);
    private LazyOptional<EnergyStorage> lazyEnergyStorage = LazyOptional.empty();

    public ReactorBlockEntity(BlockPos pPos, BlockState pBlockState, ReactorTier tier) {
        super(ModBlockEntities.REACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.reactorTier = tier;

        this.data = new MachineData()
                .add(DataType.ENERGY, energyStorage::getEnergyStored, null)
                .add(DataType.ENERGY_CAPACITY, energyStorage::getMaxEnergyStored, null)
                .add(DataType.FLUID_AMOUNT, fluidStorage::getFluidAmount, null)
                .add(DataType.FLUID_CAPACITY, fluidStorage::getCapacity, null);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("energy_storage", energyStorage.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        energyStorage.deserializeNBT(pTag.get("energy_storage"));
    }

    @Override
    public void onLoad() {
        lazyEnergyStorage = LazyOptional.of(()-> energyStorage);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
