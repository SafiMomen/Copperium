package com.example.examplemod.Blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CopperMachine extends Block {
    public CopperMachine(Properties properties) {
        super(properties);
    }

    public static void updateOrDestroy(
        BlockState oldState, 
        BlockState newState, 
        LevelAccessor level, 
        BlockPos pos,
        int flags, 
        int recursionLeft
    ) {
        if (newState == oldState) { return; }
        if (newState.isAir()) { return; }
        // blocks are unbreakable, cannot be removed once placed.
        level.setBlock(pos, newState, flags & -33, recursionLeft);
    }
}
