package com.example.examplemod.Blocks.CopperMachines;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CMachineEntity extends BlockEntity {
    public CMachineEntity(
        BlockEntityType<CMachineEntity> blockType, 
        BlockPos worldPosition, 
        BlockState blockState
    ) {
        super(blockType, worldPosition, blockState);
    }
}
