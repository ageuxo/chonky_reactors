package io.github.ageuxo.chonkyreactors.block.entity;

import io.github.ageuxo.chonkyreactors.ReactorTier;
import io.github.ageuxo.chonkyreactors.gui.menu.ReactorMenu;
import io.github.ageuxo.chonkyreactors.util.EnergyDataProvider;
import io.github.ageuxo.chonkyreactors.util.FluidDataProvider;
import io.github.ageuxo.chonkyreactors.util.VariableEnergyStorage;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ReactorBlockEntity extends BlockEntity implements MenuProvider, EnergyDataProvider, FluidDataProvider {
    private ReactorTier reactorTier;
    private final VariableEnergyStorage energyStorage = new VariableEnergyStorage(8000, 0, 64, 8000);
    private LazyOptional<EnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private final FluidTank fluidStorage = new FluidTank(8000);
    private LazyOptional<FluidTank> lazyFluidStorage = LazyOptional.empty();
    protected final ContainerData data;

    public ReactorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(pPos, pBlockState, ReactorTier.BASIC);
    }

    public ReactorBlockEntity(BlockPos pPos, BlockState pBlockState, ReactorTier tier) {
        super(ModBlockEntities.REACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.reactorTier = tier;

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> ReactorBlockEntity.this.getEnergy();
                    case 1 -> ReactorBlockEntity.this.getEnergyCapacity();
                    case 2 -> ReactorBlockEntity.this.getFluidAmount();
                    case 3 -> ReactorBlockEntity.this.getFluidCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> ReactorBlockEntity.this.setEnergy(pValue);
                    case 1 -> ReactorBlockEntity.this.setEnergyCapacity(pValue);
                    case 2 -> ReactorBlockEntity.this.setFluidAmount(pValue);
                    case 3 -> ReactorBlockEntity.this.setFluidCapacity(pValue);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
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
        lazyFluidStorage = LazyOptional.of(()-> fluidStorage);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ReactorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public VariableEnergyStorage getSourceEnergy() {
        return energyStorage;
    }

    @Override
    public FluidTank getSourceFluidTank() {
        return fluidStorage;
    }
}
