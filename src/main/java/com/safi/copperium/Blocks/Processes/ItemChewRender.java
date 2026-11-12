package com.safi.copperium.Blocks.Processes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.safi.copperium.AllBlocks;
import com.safi.copperium.Blocks.Interfaces.IRenderBlock;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ItemChewRender<T extends BlockEntity> implements IRenderBlock<T> {
    private BlockRenderDispatcher _renderDispatcher;

    public ItemChewRender(BlockRenderDispatcher renderDispatcher) {
        _renderDispatcher = renderDispatcher;
    }

    private void _applyInitialOrigin(PoseStack renderStack, float position, float rotation) {
        renderStack.pushPose();

        renderStack.translate(0.5, position, 0.5);
        /* mouth rotation, needed for second mouth */
        renderStack.mulPose(Axis.YP.rotationDegrees(rotation));
        renderStack.scale(1.15F, 1.15F, 1.15F);

        renderStack.translate(-0.5, -0.5, -0.5);
    }

    private void _applyMouthMotion(PoseStack renderStack, float biteMotion, float biteVisco) {
        // block bench pivot: 3F / 16F
        // 3 is the pivot amount, 16 is the block's texture width/length
        renderStack.translate(0F, 0F, 3F / 16F);
        renderStack.mulPose(Axis.XP.rotationDegrees(biteMotion));
        renderStack.mulPose(Axis.ZP.rotationDegrees(biteVisco));
        renderStack.translate(0F, 0F, -3F / 16F);
    }

    @Override
    public void Render(T entity, float progress, PoseStack renderStack, MultiBufferSource bufferSource,
            int packedLight, int packedOverlay) {

        float position = Mth.lerp(Mth.sin(progress * Mth.PI), 0.5F, 1.25F);

        float sinModifier = 1F - Mth.abs(progress * 2 - 1F);
        float rotation = progress * 360 * 30; // 15 swervers going up/down each
        float biteMotion = Mth.square(Mth.sin(Mth.DEG_TO_RAD * rotation)) *
                20F * sinModifier;
        float biteVisco = Mth.cos(Mth.DEG_TO_RAD * rotation) * 10F * sinModifier;

        _applyInitialOrigin(renderStack, position, 0F);
        _applyMouthMotion(renderStack, biteMotion, biteVisco);

        _renderDispatcher.renderSingleBlock(
                AllBlocks.COPPER_MOUTH.getDefaultState(),
                renderStack,
                bufferSource,
                packedLight,
                packedOverlay);

        renderStack.popPose();

        // this is for the other mouth
        _applyInitialOrigin(renderStack, position, 180F);
        _applyMouthMotion(renderStack, biteMotion, biteVisco);

        _renderDispatcher.renderSingleBlock(
                AllBlocks.COPPER_MOUTH.getDefaultState(),
                renderStack,
                bufferSource,
                packedLight,
                packedOverlay);

        renderStack.popPose();
    }
}
