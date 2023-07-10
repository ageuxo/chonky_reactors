package io.github.ageuxo.chonkyreactors.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Predicate;

public record MultiblockPart(String name, List<Block> blocks,
                             int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {

    public BlockUtil.FoundRectangle getVerticalFace(BlockGetter blockGetter, BlockPos centerPos, Direction.Axis horizontalAxis) {
        return BlockUtil.getLargestRectangleAround(centerPos,
                Direction.Axis.Y, maxY,
                horizontalAxis, getAxisMax(horizontalAxis),
                getBlockValidator(blockGetter, blocks));
    }

    public int getAxisMax(Direction.Axis axis) {
        return switch (axis) {
            case X -> maxX;
            case Y -> maxY;
            case Z -> maxZ;
        };
    }

    public int getAxisMin(Direction.Axis axis) {
        return switch (axis) {
            case X -> minX;
            case Y -> minY;
            case Z -> minZ;
        };
    }

    public static Predicate<BlockPos> getBlockValidator(BlockGetter blockGetter, List<Block> blocks) {
        return (pos) -> blocks.contains(blockGetter.getBlockState(pos).getBlock());
    }

    public static final Codec<MultiblockPart> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("name").forGetter(MultiblockPart::name),
                    RecordCodecBuilder.of(MultiblockPart::blocks, ForgeRegistries.BLOCKS.getCodec().listOf().fieldOf("blocks")),
                    Codec.INT.fieldOf("minX").forGetter(MultiblockPart::minX),
                    Codec.INT.fieldOf("maxX").forGetter(MultiblockPart::maxX),
                    Codec.INT.fieldOf("minY").forGetter(MultiblockPart::minY),
                    Codec.INT.fieldOf("maxY").forGetter(MultiblockPart::maxY),
                    Codec.INT.fieldOf("minZ").forGetter(MultiblockPart::minZ),
                    Codec.INT.fieldOf("maxZ").forGetter(MultiblockPart::maxZ)
            ).apply(instance, MultiblockPart::new));
}
