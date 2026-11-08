package com.safi.copperium;

import com.safi.copperium.Blocks.CopperMachines.CMachineEntity;
import com.safi.copperium.Blocks.CopperMachines.CMachineRender;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
    private static final Registrate REGISTRATE = ExampleMod.getRegistrate();

    public static final BlockEntityEntry<CMachineEntity> COPPER_MACHINE_ENTITY = REGISTRATE
            .blockEntity("copper_machine", CMachineEntity::new)
            .renderer(() -> CMachineRender::new)
            .validBlocks(AllBlocks.COPPER_MACHINE)
            .register();
    
    public static void register() {}
}
