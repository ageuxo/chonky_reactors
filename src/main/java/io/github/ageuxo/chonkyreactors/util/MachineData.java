package io.github.ageuxo.chonkyreactors.util;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.inventory.ContainerData;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;

public class MachineData implements ContainerData {
    protected EnumMap<DataType, Pair<IntSupplier, IntConsumer>> dataMap = new EnumMap<>(DataType.class);
    private Set<Map.Entry<DataType, Pair<IntSupplier, IntConsumer>>> test = dataMap.entrySet();
    private final int initSize;

    public MachineData(int initSize){
        this.initSize = initSize;
    }

    public MachineData(){
        this(0);
    }

    public MachineData add(DataType type, IntSupplier supplier, IntConsumer consumer) {
        dataMap.put(type, Pair.of(supplier, consumer));
        return this;
    }

    public int get(DataType type) {
        return dataMap.containsKey(type) ? dataMap.get(type).first().getAsInt() : 0;
    }

    @Override
    public int get(int ordinal) {
        return get(DataType.fromOrdinal(ordinal));
    }

    public void set(DataType type, int value){
        dataMap.get(type).second().accept(value);
    }

    @Override
    public void set(int ordinal, int value) {
        set(DataType.fromOrdinal(ordinal), value);
    }

    public int getCount() {
        return dataMap.isEmpty() ? initSize : dataMap.size();
    }

    public Set<DataType> getValues(){
        return dataMap.keySet();
    }

}
