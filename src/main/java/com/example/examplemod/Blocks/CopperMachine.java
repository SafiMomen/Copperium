package com.example.examplemod.Blocks;

import java.util.Objects;

import com.example.examplemod.AllItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CopperMachine extends Block {
    public CopperMachine(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack, 
        BlockState _state, 
        Level level, 
        BlockPos pos,
        Player player, 
        InteractionHand hand, 
        BlockHitResult _hitResult
    ) {
        // return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        if (!stack.is(Objects.requireNonNull(
            Items.COPPER_BLOCK))) {
            
            return ItemInteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) 
        {
            serverLevel.playSound(
                player, 
                Objects.requireNonNull(pos), 
                Objects.requireNonNull(
                    SoundEvents.ENCHANTMENT_TABLE_USE
                ), 
                SoundSource.BLOCKS
            ); // TODO: add better sound effects. 
               
            double cx = pos.getX() + 0.5;
            double cy = pos.getY() + 0.5;
            double cz = pos.getZ() + 0.5;

            serverLevel.sendParticles(
                Objects.requireNonNull(ParticleTypes.ENCHANT), 
                cx, cy, cz, 
                30, // number of particles
                0.2, 0.2, 0.2, 
                0.5
            ); // particle sounds

            if (!player.getAbilities().instabuild) {
                stack.shrink(1); // reduce the item from hand 
            }
            
            ItemEntity droppedItem = new ItemEntity(
                serverLevel, 
                cx, cy + 0.5, cz, 
                new ItemStack(Objects.requireNonNull(
                    AllItems.COPPER_TOTEM.get()), 1),
                0.0, 0.25, 0.0
            );
            droppedItem.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(droppedItem);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }
}
