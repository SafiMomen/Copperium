package com.safi.copperium.Blocks.Interfaces;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IRenderBlock<T extends BlockEntity> {
    public void Render(
            T entity,
            float progress,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay);
}
