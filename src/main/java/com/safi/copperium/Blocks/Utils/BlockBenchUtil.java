package com.safi.copperium.Blocks.Utils;

import java.util.Objects;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.level.block.Block;

public class BlockBenchUtil {
    public static <T extends Block> NonNullBiConsumer<DataGenContext<Block, T>, RegistrateBlockstateProvider> GenerateBlockState(
        String modLocationName
    ) {
        return (ctx, provider) -> {
            /* 
            fuck ass mod loader causes issues when
            using block-bench models.
             */ 
            var model = provider.models().getExistingFile(
                Objects.requireNonNull(provider.modLoc(Objects.requireNonNull(modLocationName)))
            );

            provider.simpleBlock(Objects.requireNonNull(ctx.get()), Objects.requireNonNull(model));
        };
    }
}
