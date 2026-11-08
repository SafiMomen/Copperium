package com.safi.copperium.Blocks.CopperMachines;

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

public class CMachineEntity extends BlockEntity {
    private long _processingStartTick = 1L;

    public CMachineEntity(
        BlockEntityType<CMachineEntity> blockType, 
        BlockPos worldPosition, 
        BlockState blockState
    ) {
        super(blockType, worldPosition, blockState);
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

        return state.getValue(CMachine.IS_PROCESSING);
    }

    public float GetProgress(float partialTick) {
        if (level == null || !IsProcessing()) {
            return 0.0f;
        }

        float elapsedTime = level.getGameTime() - 
                this._processingStartTick + partialTick;
        float progressAlpha = elapsedTime / CMachine.PROCESSING_TIME;
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
