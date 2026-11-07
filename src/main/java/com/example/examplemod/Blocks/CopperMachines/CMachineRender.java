package com.example.examplemod.Blocks.CopperMachines;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class CMachineRender implements BlockEntityRenderer<CMachineEntity> {
    private BlockRenderDispatcher _renderDispatcher;

    public CMachineRender(
        BlockEntityRendererProvider.Context context
    ) {
        _renderDispatcher = context.getBlockRenderDispatcher();
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

        float position = Mth.lerp(progressAlpha, 1.25F, 0.75F);

        renderStack.pushPose();
        renderStack.translate(0.5, position, 0.5);
        renderStack.scale(0.5F, 0.5F, 0.5F);
        renderStack.translate(-0.5, -0.5, -0.5);
        _renderDispatcher.renderSingleBlock(
            Blocks.COPPER_BLOCK.defaultBlockState(),
            renderStack, 
            bufferSource, 
            packedLight, 
            packedOverlay);
        renderStack.popPose();
    }
}
