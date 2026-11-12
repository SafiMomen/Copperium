package com.safi.copperium.Blocks.CopperMachine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.safi.copperium.Blocks.Processes.BlockProcessEntity;
import com.safi.copperium.Blocks.Processes.ItemChewRender;
import com.safi.copperium.Blocks.Processes.ItemFeedRender;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CMachineRender implements BlockEntityRenderer<BlockProcessEntity> {
    private ItemFeedRender<BlockProcessEntity> _itemFeedRender;
    private ItemChewRender<BlockProcessEntity> _itemChewRender;

    public CMachineRender(
        BlockEntityRendererProvider.Context context
    ) {
        _itemFeedRender = new ItemFeedRender<BlockProcessEntity>(
            context.getBlockRenderDispatcher()
        );
        _itemChewRender = new ItemChewRender<BlockProcessEntity>(
            context.getBlockRenderDispatcher()
        );
    }

    @Override
    public void render(
        BlockProcessEntity blockEntity, 
        float partialTick, 
        PoseStack renderStack, 
        MultiBufferSource bufferSource, 
        int packedLight, 
        int packedOverlay
    ) {
        if (!blockEntity.IsProcessing()) {
            return;
        }
        float progressAlpha = blockEntity.GetProgress(partialTick);
        // the typical block going down animation
        //TODO: consider adding a direction of input
        _itemFeedRender.Render(blockEntity, progressAlpha, 
            renderStack, bufferSource, packedLight, packedOverlay);
        // the mouth animation of the chewing
        //TODO: consider adding a direction of input
        _itemChewRender.Render(blockEntity, progressAlpha, 
            renderStack, bufferSource, packedLight, packedOverlay);
    }
}
