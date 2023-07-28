package io.github.ageuxo.chonkyreactors.util;

import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface FluidDataProvider {
    FluidTank getSourceFluidTank();

    default int getFluidAmount() {
        return getSourceFluidTank().getFluidAmount();
    }

    default int getFluidCapacity() {
        return getSourceFluidTank().getCapacity();
    }

    default void setFluidAmount(int amount){
        getSourceFluidTank().getFluid().setAmount(amount);
    }

    default void setFluidCapacity(int capacity){
        getSourceFluidTank().setCapacity(capacity);
    }

}
