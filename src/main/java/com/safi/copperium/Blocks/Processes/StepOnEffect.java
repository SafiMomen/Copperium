package com.safi.copperium.Blocks.Processes;

import com.safi.copperium.Blocks.Interfaces.IEffectBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;

public class StepOnEffect implements IEffectBlock {
    private DamageSource _damageSource;
    private float _hurtAmount = 1.0F;
    private float _flingModifier = 1.0F;

    public StepOnEffect(float hurtAmount, float flingModifier) {
        _hurtAmount = hurtAmount;    
        _flingModifier = flingModifier;
    }

    public void SetDamageSource(DamageSource damageSource) {
        _damageSource = damageSource;
    }

    @Override
    public void Activate(Level level, BlockPos _pos, BlockState _state, Entity entity) {
        Activate(level, entity);
    }

    @Override
    public void Activate(Level level, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) { return; }

        if (!(entity instanceof Player player)) { return; }

        player.hurt(_damageSource, _hurtAmount);
        
        var vx = serverLevel.getRandom().nextInt(-1, 1) * _flingModifier;
        var vz = serverLevel.getRandom().nextInt(-1, 1) * _flingModifier;
        player.push(vx, _flingModifier, vz);
        player.hurtMarked = true;
    }
}
