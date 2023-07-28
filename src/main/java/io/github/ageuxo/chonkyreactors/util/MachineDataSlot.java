package io.github.ageuxo.chonkyreactors.util;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DataSlot;

public class MachineDataSlot extends DataSlot{
    private final MachineData data;
    private final int id;

    public MachineDataSlot(ContainerData data, int id){
        this.data = (MachineData) data;
        this.id = id;
    }

    @Override
    public int get() {
        return data.get(id);
    }

    @Override
    public void set(int pValue) {
        data.set(id, pValue);
    }
}
