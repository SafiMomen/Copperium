package com.safi.copperium;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Copperium.MODID)
public class Copperium {
    public static final String MODID = "copperium";
    
    public static final Registrate REGISTRATE = 
        Registrate.create(Copperium.MODID);

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Copperium(IEventBus modEventBus, ModContainer modContainer) {
        AllItems.register();
        AllBlocks.register();
        AllBlockEntityTypes.register();

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        // LOGGER.info("HELLO from server starting");
        return;
    }

    public static Registrate getRegistrate() {
        return Copperium.REGISTRATE;
    }
}
