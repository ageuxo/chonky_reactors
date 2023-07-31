package io.github.ageuxo.chonkyreactors.block.custom;

import io.github.ageuxo.chonkyreactors.block.entity.ReactorBlockEntity;
import io.github.ageuxo.chonkyreactors.block.entity.ReactorSubBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReactorSubBlock extends BaseEntityBlock {
    public ReactorBlockEntity controller;

    public ReactorSubBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ReactorSubBlockEntity(pPos, pState);
    }
}
