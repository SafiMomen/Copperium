package com.safi.copperium.Blocks.CopperMachine;

import com.mojang.blaze3d.vertex.PoseStack;
import com.safi.copperium.Blocks.Renders.ItemChewRender;
import com.safi.copperium.Blocks.Renders.ItemFeedRender;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CMachineRender implements BlockEntityRenderer<CMachineEntity> {
    private ItemFeedRender _itemFeedRender;
    private ItemChewRender _itemChewRender;

    public CMachineRender(
        BlockEntityRendererProvider.Context context
    ) {
        _itemFeedRender = new ItemFeedRender(
            context.getBlockRenderDispatcher()
        );
        _itemChewRender = new ItemChewRender(
            context.getBlockRenderDispatcher()
        );
    }

    @Override
    public void render(
        CMachineEntity blockEntity, 
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
