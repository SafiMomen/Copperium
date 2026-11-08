package com.safi.copperium.Blocks.CopperMachines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.safi.copperium.Blocks.Renders.ItemFeedRender;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;

public class CMachineRender implements BlockEntityRenderer<CMachineEntity> {
    private ItemFeedRender _itemFeedRender;

    public CMachineRender(
        BlockEntityRendererProvider.Context context
    ) {
        _itemFeedRender = new ItemFeedRender(
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

        _itemFeedRender.Render(blockEntity, progressAlpha, 
            renderStack, bufferSource, packedLight, packedOverlay);
    }
}
