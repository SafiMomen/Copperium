package com.safi.copperium.Blocks.Processes;

import com.safi.copperium.Blocks.Interfaces.IProcessBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BlockProcessEntity extends BlockEntity {
    private long _processingStartTick = -1L;
    private IProcessBlock _machineBlock;

    public BlockProcessEntity(
        BlockEntityType<BlockProcessEntity> blockType, 
        BlockPos worldPosition, 
        BlockState blockState
    ) {
        super(blockType, worldPosition, blockState);
        
        // this entity won't be registered unless it has IProcessBlock anyway.
        _machineBlock = (IProcessBlock)blockState.getBlock();
    }

    public void StartProcessing() {
        if (level == null || level.isClientSide()) {
            return;
        }

        _processingStartTick = level.getGameTime();
        setChanged();

        BlockState state = this.getBlockState();
        
        level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
    }

    public boolean IsProcessing() {
        if (this._processingStartTick < 0L) {
            return false;
        }
        BlockState state = this.getBlockState();

        return state.getValue(_machineBlock.IsProcessing());
    }

    public float GetProgress(float partialTick) {
        if (level == null || !IsProcessing()) {
            return 0.0f;
        }

        float elapsedTime = level.getGameTime() - 
                this._processingStartTick + partialTick;
        float progressAlpha = elapsedTime / _machineBlock.GetProcessTime();
        return Mth.clamp(progressAlpha, 0.0F, 1.0F);
    }

    @Override
    protected void saveAdditional(
        CompoundTag tag,
        HolderLookup.Provider registries
    ) {
        super.saveAdditional(tag, registries);
        tag.putLong("ProcessingStartTick", _processingStartTick);
    }

    @Override
    protected void loadAdditional(
        CompoundTag tag,
        HolderLookup.Provider registries
    ) {
        super.loadAdditional(tag, registries);

        _processingStartTick = tag.contains("ProcessingStartTick")
            ? tag.getLong("ProcessingStartTick") : -1L;
    }

    @Override
    public CompoundTag getUpdateTag(
        HolderLookup.Provider registries
    ) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
