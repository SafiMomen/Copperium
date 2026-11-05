package com.example.examplemod;

import com.example.examplemod.Blocks.CopperMachine;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;

public class AllBlockEntities {
    private static final Registrate REGISTRATE = ExampleMod.getRegistrate();

    // provided with a block of copper, it will give back a copper gear 
    // it takes time to process, or should take time to process
    public static final BlockEntry<CopperMachine> COPPER_MACHINE = REGISTRATE
        .block("copper_machine", CopperMachine::new)
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

    public static void register() {}
}
