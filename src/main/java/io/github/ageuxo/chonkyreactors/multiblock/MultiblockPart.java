package io.github.ageuxo.chonkyreactors.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record MultiblockPart(String name, List<TagEntry> entries,
                             int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {

    @SuppressWarnings("deprecation")
    public static MultiblockPart create(String name, List<Block> blocks, int minX, int maxX, int minY, int maxY, int minZ, int maxZ){
        List<TagEntry> list = new ArrayList<>();
        blocks.forEach(block -> list.add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(block))));
        return new MultiblockPart(name, list, minX, maxX, minY, maxY, minZ, maxZ);
    }

    public static MultiblockPart createFromTags(String name, List<TagKey<Block>> blockTagKeys, int minX, int maxX, int minY, int maxY, int minZ, int maxZ){
        List<TagEntry> list = new ArrayList<>();
        blockTagKeys.forEach(tagKey -> list.add(TagEntry.tag(tagKey.location())));
        return new MultiblockPart(name, list, minX, maxX, minY, maxY, minZ, maxZ);
    }

    public BlockUtil.FoundRectangle getVerticalFace(BlockGetter blockGetter, BlockPos centerPos, Direction.Axis horizontalAxis) {
        return BlockUtil.getLargestRectangleAround(centerPos,
                Direction.Axis.Y, maxY/2,
                horizontalAxis, getAxisMax(horizontalAxis)/2,
                getBlockValidator(blockGetter, this.getValidBlocks()));
    }

    public List<Block> getValidBlocks(){
        List<Block> ret = new ArrayList<>();
        this.entries.forEach(tagEntry -> tagEntry.build(blockTagEntryLookup, (ret::add)));
        return ret;
    }

    private static final TagEntry.Lookup<Block> blockTagEntryLookup = new TagEntry.Lookup<>() {
        @Override
        @SuppressWarnings("deprecation")
        public Block element(@NotNull ResourceLocation pElementLocation) {
            return BuiltInRegistries.BLOCK.get(pElementLocation);
        }

        @SuppressWarnings("DataFlowIssue")
        @Override
        public List<Block> tag(@NotNull ResourceLocation pTagLocation) {
            return ForgeRegistries.BLOCKS.tags().getTag(TagKey.create(Registries.BLOCK, pTagLocation)).stream().toList();
        }
    };

    public int getAxisMax(Direction.Axis axis) {
        return switch (axis) {
            case X -> maxX;
            case Y -> maxY;
            case Z -> maxZ;
        };
    }

    @SuppressWarnings("unused")
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
                    TagEntry.CODEC.listOf().fieldOf("entries").forGetter(MultiblockPart::entries),
                    Codec.INT.fieldOf("minX").forGetter(MultiblockPart::minX),
                    Codec.INT.fieldOf("maxX").forGetter(MultiblockPart::maxX),
                    Codec.INT.fieldOf("minY").forGetter(MultiblockPart::minY),
                    Codec.INT.fieldOf("maxY").forGetter(MultiblockPart::maxY),
                    Codec.INT.fieldOf("minZ").forGetter(MultiblockPart::minZ),
                    Codec.INT.fieldOf("maxZ").forGetter(MultiblockPart::maxZ)
            ).apply(instance, MultiblockPart::new));
}
