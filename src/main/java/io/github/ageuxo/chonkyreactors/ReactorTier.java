package io.github.ageuxo.chonkyreactors;

public enum ReactorTier {
    BASIC("basic", 4, 8000, 64);

    private final String name;
    private final int moduleSlots;
    private final int energyCapacity;
    private final int maxExtract;

    ReactorTier(String name, int moduleSlots, int baseEnergyCapacity, int baseMaxExtract) {
        this.name = name;
        this.moduleSlots = moduleSlots;
        this.energyCapacity = baseEnergyCapacity;
        this.maxExtract = baseMaxExtract;
    }

    public String getName() {
        return name;
    }

    public int getModuleSlots() {
        return moduleSlots;
    }

    public int getEnergyCapacity() {
        return energyCapacity;
    }

    public int getMaxExtract() {
        return maxExtract;
    }
}
