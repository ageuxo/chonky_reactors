package io.github.ageuxo.chonkyreactors.util;

public interface EnergyDataProvider {
    VariableEnergyStorage getSourceEnergy();

    default int getEnergy() {
        return getSourceEnergy().getEnergyStored();
    }

    default int getEnergyCapacity(){
        return getSourceEnergy().getMaxEnergyStored();
    }

    default void setEnergy(int energy){
        getSourceEnergy().setEnergy(energy);
    }

    default void setEnergyCapacity(int capacity){
        getSourceEnergy().setCapacity(capacity);
    }
}
