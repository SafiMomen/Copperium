package com.safi.copperium.Items;

import java.util.Objects;

import com.safi.copperium.ExampleMod;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber (modid = ExampleMod.MODID)
public class CopperTotem extends Item {
    public CopperTotem(Properties properties) {
        super(properties);
    }

    public static void attemptRevive(
        LivingDeathEvent event, 
        Player player, 
        ItemStack totem
    ) {
        event.setCanceled(true);
        totem.shrink(1);
        player.setHealth(0.5F);
        player.removeAllEffects();
        player.level().broadcastEntityEvent(player, (byte) 35);
    }
    
    @SubscribeEvent 
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (player.level().isClientSide()) { 
            return;
        }
        if (event.getSource().is(Objects.requireNonNull(
            DamageTypeTags.BYPASSES_INVULNERABILITY))) {

            return;
        }
        
        ItemStack itemOnHand = player.getMainHandItem();
        ItemStack itemOnOffHand = player.getOffhandItem();
        if (itemOnHand.getItem() instanceof CopperTotem) 
        {
            CopperTotem.attemptRevive(event, player, itemOnHand);
        } 
        else if (itemOnOffHand.getItem() instanceof CopperTotem) 
        {
            CopperTotem.attemptRevive(event, player, itemOnOffHand);
        }
    }
}
