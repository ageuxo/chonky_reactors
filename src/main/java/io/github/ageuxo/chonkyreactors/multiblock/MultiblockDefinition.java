package io.github.ageuxo.chonkyreactors.multiblock;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record MultiblockDefinition(String name, List<MultiblockPart> parts) {
    public static final Codec<MultiblockDefinition> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("name").forGetter(MultiblockDefinition::name),
                    Codec.list(MultiblockPart.CODEC).fieldOf("parts").forGetter(MultiblockDefinition::parts)
            ).apply(instance, MultiblockDefinition::new));
}
