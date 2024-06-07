package com.alexlightovich.shawrmamod.block;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.block.VertelBlock;
import com.alexlightovich.shawrmamod.block.custom.CabbageCropBlock;
import com.alexlightovich.shawrmamod.block.custom.CucumberCropBlock;
import com.alexlightovich.shawrmamod.block.custom.TomatoCropBlock;
import com.alexlightovich.shawrmamod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ShawrmaMod.MODID);

    public static final RegistryObject<Block> SPEAKER_BLOCK = registerBlock("speaker_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(9f)));

    public static final RegistryObject<Block> VERTEL = registerBlock("vertel_block", () -> new VertelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).lightLevel((state) -> state.getValue(VertelBlock.IS_BURNING)? 7 : 0)));
    public static final RegistryObject<Block> TOMATO_CROP = BLOCKS.register("tomato_crop", () -> new TomatoCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));
    public static final RegistryObject<Block> CABBAGE_CROP = BLOCKS.register("cabbage_crop", () -> new CabbageCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));
    public static final RegistryObject<Block> CUCUMBER_CROP = BLOCKS.register("cucumber_crop", () -> new CucumberCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));
    //public static final RegistryObject<Block> LAVASH_FURNACE = registerBlock("lavash_furnace", () -> new VertelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).lightLevel((state) -> state.getValue(VertelBlock.IS_BURNING)? 15 : 0)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
