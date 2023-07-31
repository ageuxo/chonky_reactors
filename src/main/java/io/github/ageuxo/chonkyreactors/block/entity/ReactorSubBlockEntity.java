package io.github.ageuxo.chonkyreactors.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ReactorSubBlockEntity extends BlockEntity {
    public ReactorSubBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_SUB_ENTITY.get(), pPos, pBlockState);
    }
}
