package com.safi.copperium.Blocks.Renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.safi.copperium.Blocks.CopperMachines.CMachineEntity;
import com.safi.copperium.Blocks.Interfaces.IRenderBlock;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;

public class ItemFeedRender implements IRenderBlock<CMachineEntity> {
    private BlockRenderDispatcher _renderDispatcher;

    public ItemFeedRender(BlockRenderDispatcher renderDispatcher) {
        _renderDispatcher = renderDispatcher;
    }

    @Override
    public void Render(CMachineEntity entity, float progress, PoseStack renderStack, MultiBufferSource bufferSource,
            int packedLight, int packedOverlay) {

        float position = Mth.lerp(progress, 1.25F, 0.75F);

        renderStack.pushPose();

        renderStack.translate(0.5, position, 0.5);
        renderStack.scale(0.5F, 0.5F, 0.5F);
        renderStack.translate(-0.5, -0.5, -0.5);
        
        _renderDispatcher.renderSingleBlock(
            Blocks.COPPER_BLOCK.defaultBlockState(),
            renderStack, 
            bufferSource, 
            packedLight, 
            packedOverlay
        );
        renderStack.popPose();
    }
}
