package com.safi.copperium.Blocks.Interfaces;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IEffectBlock {
    public void Activate(
            Level level,
            BlockPos pos,
            BlockState state,
            Entity entity);
    public void Activate(Level level, Entity entity);
}
