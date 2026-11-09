package com.safi.copperium.Blocks.Interfaces;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface IProcessBlock {
    public float GetProcessTime();
    public BooleanProperty IsProcessing();
}
