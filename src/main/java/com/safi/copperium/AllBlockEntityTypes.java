package com.safi.copperium;

import com.safi.copperium.Blocks.CopperMachine.CMachineEntity;
import com.safi.copperium.Blocks.CopperMachine.CMachineRender;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
    private static final Registrate REGISTRATE = Copperium.getRegistrate();

    public static final BlockEntityEntry<CMachineEntity> COPPER_MACHINE_ENTITY = REGISTRATE
            .blockEntity("copper_machine", CMachineEntity::new)
            .renderer(() -> CMachineRender::new)
            .validBlocks(AllBlocks.COPPER_MACHINE)
            .register();
    
    public static void register() {}
}
