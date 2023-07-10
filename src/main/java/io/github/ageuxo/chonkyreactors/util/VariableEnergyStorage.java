package io.github.ageuxo.chonkyreactors.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.energy.EnergyStorage;

public class VariableEnergyStorage extends EnergyStorage {
    static final String nbtEnergy = "energy";
    static final String nbtCapacity = "capacity";
    static final String nbtExtract = "maxExtract";
    static final String nbtReceive = "maxReceive";

    public VariableEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setCapacity(int newCapacity){
        this.capacity = newCapacity;
    }

    public void setMaxExtract(int newMaxExtract){
        this.maxExtract = newMaxExtract;
    }

    public void setMaxReceive(int newMaxReceive){
        this.maxReceive = newMaxReceive;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof CompoundTag tag){
            this.energy = tag.getInt(nbtEnergy);
            this.capacity = tag.getInt(nbtCapacity);
            this.maxExtract = tag.getInt(nbtExtract);
            this.maxReceive = tag.getInt(nbtReceive);
        }
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(nbtEnergy, this.energy);
        tag.putInt(nbtCapacity, this.capacity);
        tag.putInt(nbtExtract, this.maxExtract);
        tag.putInt(nbtReceive, this.maxReceive);
        return tag;
    }
}
