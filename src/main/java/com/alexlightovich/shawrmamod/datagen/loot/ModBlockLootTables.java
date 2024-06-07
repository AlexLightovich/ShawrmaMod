package com.alexlightovich.shawrmamod.datagen.loot;

import com.alexlightovich.shawrmamod.block.ModBlocks;
import com.alexlightovich.shawrmamod.block.custom.CabbageCropBlock;
import com.alexlightovich.shawrmamod.block.custom.CucumberCropBlock;
import com.alexlightovich.shawrmamod.block.custom.TomatoCropBlock;
import com.alexlightovich.shawrmamod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.VERTEL.get());
        this.dropSelf(ModBlocks.SPEAKER_BLOCK.get());


//        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
//                .hasBlockStateProperties(ModBlocks.TOMATO_CROP.get())
//                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TomatoCropBlock.AGE, 5));

        this.add(ModBlocks.TOMATO_CROP.get(), createCropDrops(ModBlocks.TOMATO_CROP.get(), ModItems.TOMATO.get(),
                ModItems.TOMATO_SEEDS.get(), makeLootitemCondition(ModBlocks.TOMATO_CROP.get(), TomatoCropBlock.AGE,5)));

        this.add(ModBlocks.CABBAGE_CROP.get(), createCropDrops(ModBlocks.CABBAGE_CROP.get(), ModItems.CABBAGE.get(),
                ModItems.CABBAGE_SEEDS.get(), makeLootitemCondition(ModBlocks.CABBAGE_CROP.get(), CabbageCropBlock.AGE,5)));

        this.add(ModBlocks.CUCUMBER_CROP.get(), createCropDrops(ModBlocks.CUCUMBER_CROP.get(), ModItems.CUCUMBER.get(),
                ModItems.CUCUMBER_SEEDS.get(), makeLootitemCondition(ModBlocks.CUCUMBER_CROP.get(), CucumberCropBlock.AGE,5)));
    }

    private LootItemCondition.Builder makeLootitemCondition(Block block, Property<Integer> ageProperty, int age) {
       return LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(block)
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ageProperty, age));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}