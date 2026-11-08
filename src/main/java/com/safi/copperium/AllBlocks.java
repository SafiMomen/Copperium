package com.safi.copperium;

import com.safi.copperium.Blocks.CopperMachines.CMachine;
import com.safi.copperium.Blocks.CopperMachines.CMachineEntity;
import com.safi.copperium.Blocks.CopperMachines.CMachineRender;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;

public class AllBlocks {
    private static final Registrate REGISTRATE = Copperium.getRegistrate();

    // provided with a block of copper, it will give back a copper gear
    // it takes time to process, or should take time to process
    public static final BlockEntry<CMachine> COPPER_MACHINE = REGISTRATE
        .block("copper_machine", CMachine::new)
        .blockstate((ctx, provider) -> {
            /* 
            fuck ass mod loader causes issues when
            using block-bench models.
             */ 
            var model = provider.models().getExistingFile(
                provider.modLoc("block/copper_machine")
            );

            provider.simpleBlock(ctx.get(), model);
        })
        .initialProperties(() -> Blocks.DISPENSER)
        .properties(properties -> properties
            // and afterwards should also be unbreakable in the world.
            .strength(-1.0F, 3600000.0F)
            // no clue what this does, but create mod uses these?
            .noOcclusion()
            .mapColor(MapColor.PODZOL))
        .recipe((ctx, provider) -> {
            ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ctx.get())
                .pattern("IBI")
                .pattern("BNB")
                .pattern("IBI")
                .define('B', Blocks.COPPER_BLOCK)
                .define('N', Items.NETHER_STAR)
                .define('I', Items.COPPER_INGOT)
                // requries nether-star to balance the mod-pack more
                // shouldn't be able to craft right out of the start.
                .unlockedBy(
                    "has_nether_star", 
                    RegistrateRecipeProvider.has(Items.NETHER_STAR)
                ).save(provider);
        })
        .lang("Copper Machine")
        .simpleItem()
        .register();
    /*
     In compliment with CMachine 
     Do not add any recipes
     Do not add any items,
     Will be rendered with the mouth
    */
    public static final BlockEntry<Block> COPPER_MOUTH = REGISTRATE
        .block("copper_mouth", Block::new)
        .blockstate((ctx, provider) -> {
            /* 
            fuck ass mod loader causes issues when
            using block-bench models.
             */ 
            var model = provider.models().getExistingFile(
                provider.modLoc("block/copper_consumer")
            );

            provider.simpleBlock(ctx.get(), model);
        }).register();

    public static void register() {}
}
