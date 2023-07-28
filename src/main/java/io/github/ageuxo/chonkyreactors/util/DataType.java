package io.github.ageuxo.chonkyreactors.util;

public enum DataType {
    PROGRESS,
    MAX_PROGRESS,
    ENERGY,
    ENERGY_CAPACITY,
    FLUID_AMOUNT,
    FLUID_CAPACITY;

    static final DataType[] CACHE = DataType.values();

    public static DataType fromOrdinal(int ordinal) {
        return CACHE[ordinal];
    }
}
