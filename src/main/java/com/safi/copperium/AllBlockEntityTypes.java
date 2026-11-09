package com.safi.copperium;

import com.safi.copperium.Blocks.CopperGrinder.CGrinderRender;
import com.safi.copperium.Blocks.CopperMachine.CMachineRender;
import com.safi.copperium.Blocks.Processes.BlockProcessEntity;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class AllBlockEntityTypes {
    private static final Registrate REGISTRATE = Copperium.getRegistrate();

    public static final BlockEntityEntry<BlockProcessEntity> COPPER_MACHINE_ENTITY = REGISTRATE
            .blockEntity("copper_machine", BlockProcessEntity::new)
            .renderer(() -> CMachineRender::new)
            .validBlocks(AllBlocks.COPPER_MACHINE)
            .register();

    public static final BlockEntityEntry<BlockProcessEntity> COPPER_GRINDER_ENTITY = REGISTRATE
            .blockEntity("copper_grinder", BlockProcessEntity::new)
            .renderer(() -> CGrinderRender::new)
            .validBlocks(AllBlocks.COPPER_GRINDER)
            .register();
    
    public static void register() {}
}
