package com.example.examplemod.Blocks.CopperMachines;

import java.util.Objects;

import javax.annotation.Nullable;

import com.example.examplemod.AllBlockEntityTypes;
import com.example.examplemod.AllItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CMachine extends Block implements EntityBlock {
    public static final BooleanProperty IS_PROCESSING = 
        BooleanProperty.create("processing_state");

    public static final int PROCESSING_TIME = 4 * 20; // 5 seconds
    
    public static final Item ITEM_TO_FEED = Items.COPPER_BLOCK;
    
    public CMachine(Properties properties) {
        super(properties);

        BlockState blockDefinition = stateDefinition.any()
            .setValue(IS_PROCESSING, false);

        this.registerDefaultState(
            Objects.requireNonNull(blockDefinition)
        ); 
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(
        BlockPos worldPosition, BlockState blockState) {

        // assuming this gets called once
        // and once called, it connects approate entity object.
        return new CMachineEntity(
            AllBlockEntityTypes.COPPER_MACHINE_ENTITY.get(), 
            worldPosition, 
            blockState);
    }
    
    @Override
    protected void createBlockStateDefinition(
        Builder<Block, BlockState> builder
    ) {
        builder.add(IS_PROCESSING);
    }

    private void processBlockInteraction(ServerLevel serverLevel, BlockPos pos) {
        serverLevel.playSound(
            null, // should play to all players.
            Objects.requireNonNull(pos), 
            Objects.requireNonNull(
                SoundEvents.ENCHANTMENT_TABLE_USE
            ), 
            SoundSource.BLOCKS
        ); 
            
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
    }

    private void giveResultItem(ServerLevel serverLevel, BlockPos pos) {
        serverLevel = Objects.requireNonNull(serverLevel);

        double cx = pos.getX() + 0.5;
        double cy = pos.getY() + 0.5;
        double cz = pos.getZ() + 0.5;
        
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
    
    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack, 
        BlockState state, 
        Level level, 
        BlockPos pos,
        Player player, 
        InteractionHand hand, 
        BlockHitResult _hitResult
    ) {
        if (!stack.is(Objects.requireNonNull(ITEM_TO_FEED))) {
            return ItemInteractionResult.FAIL;
        }
        if (state.getValue(IS_PROCESSING)) 
        {
            // should not be able to click 
            // during proceesing time.
            return ItemInteractionResult.FAIL;
        }
        if (level instanceof ServerLevel serverLevel) 
        {
            serverLevel.setBlock(
                pos, 
                state.setValue(IS_PROCESSING, true),
                UPDATE_ALL
            );
            if (serverLevel.getBlockEntity(pos) instanceof CMachineEntity machine) 
            {
                machine.StartProcessing();
            }

            this.processBlockInteraction(serverLevel, pos);

            serverLevel.scheduleTick(pos, this, PROCESSING_TIME);

            if (!player.getAbilities().instabuild) {
                stack.shrink(1); // reduce the item from hand 
            }
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) { return; }
        if (!(entity instanceof ItemEntity itemEntity)) { return; }

        if (state.getValue(IS_PROCESSING)) { return; }

        ItemStack itemStack = itemEntity.getItem();
        if (!itemStack.is(Objects.requireNonNull(ITEM_TO_FEED))) { return; }
        if (!(serverLevel.getBlockEntity(pos) 
            instanceof CMachineEntity machineEntity)) { 

            return; 
        }

        serverLevel.setBlock(
            pos, 
            state.setValue(IS_PROCESSING, true),
            UPDATE_ALL
        );
        machineEntity.StartProcessing();
        this.processBlockInteraction(serverLevel, pos);

        serverLevel.scheduleTick(pos, this, PROCESSING_TIME);

        itemEntity.discard();
    }

    @Override
    protected void tick(
        BlockState state, 
        ServerLevel level, 
        BlockPos pos, 
        RandomSource random
    ) {
        if (!state.getValue(IS_PROCESSING)) {
            return;
        }
        level.setBlock(pos, state.setValue(IS_PROCESSING, false), UPDATE_ALL);

        this.giveResultItem(level, pos);
    }
}
