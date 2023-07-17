package io.github.ageuxo.chonkyreactors.item.crafting;

import net.minecraft.world.SimpleContainer;

import java.util.stream.IntStream;

public class SimpleMachineContainer extends SimpleContainer {
    private final int inputSize;
    private final int[] inputSlots;
    private final int extraSize;
    private final int[] extraSlots;
    private final int outputSize;
    private final int[] outputSlots;

    public SimpleMachineContainer(int inputSize, int extraSize, int outputSize) {
        super(inputSize + extraSize + outputSize);
        this.inputSize = inputSize;
        this.extraSize = extraSize;
        this.outputSize = outputSize;

        this.inputSlots = IntStream.range(0, inputSize).toArray();
        this.extraSlots = IntStream.range(inputSize, inputSize+extraSize).toArray();
        this.outputSlots = IntStream.range(inputSize+extraSize, inputSize+extraSize+outputSize).toArray();
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getInputOffset(){
        return 0;
    }

    public int[] getInputSlots() {
        return inputSlots;
    }

    public int getExtraSize() {
        return extraSize;
    }

    public int getExtraOffset(){
        return inputSize-1;
    }

    public int[] getExtraSlots() {
        return extraSlots;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public int getOutputOffset(){
        return inputSize+extraSize-1;
    }

    public int[] getOutputSlots() {
        return outputSlots;
    }
}
