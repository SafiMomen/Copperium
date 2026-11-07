package com.example.examplemod.Blocks.CopperMachines;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CMachineRender implements BlockEntityRenderer<CMachineEntity> {
    public CMachineRender(
        BlockEntityRendererProvider.Context context
    ) {}

    @Override
    public void render(
        CMachineEntity blockEntity, 
        float partialTick, 
        PoseStack stack, 
        MultiBufferSource bufferSource, 
        int packedLight, 
        int packedOverlay
    ) {
        // TODO Auto-generated method stub
        
    }
}
