package io.github.ageuxo.chonkyreactors.item.crafting;

import net.minecraft.world.SimpleContainer;

public class SimpleMachineContainer extends SimpleContainer {
    private final int inputSize;
    private final int extraSize;
    private final int outputSize;

    public SimpleMachineContainer(int inputSize, int extraSize, int outputSize) {
        super(inputSize + extraSize + outputSize);
        this.inputSize = inputSize;
        this.extraSize = extraSize;
        this.outputSize = outputSize;
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getExtraSize() {
        return extraSize;
    }

    public int getOutputSize() {
        return outputSize;
    }
}
