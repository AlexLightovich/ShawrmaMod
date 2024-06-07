package com.alexlightovich.shawrmamod.datagen;

import com.alexlightovich.shawrmamod.block.ModBlocks;
import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.block.VertelBlock;
import com.alexlightovich.shawrmamod.block.custom.CabbageCropBlock;
import com.alexlightovich.shawrmamod.block.custom.CucumberCropBlock;
import com.alexlightovich.shawrmamod.block.custom.TomatoCropBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    private static final String TOMATO = "tomato";
    private static final String CUCUMBER = "cucumber";
    private static final String CABBAGE = "cabbage";

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ShawrmaMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlockWithItem(ModBlocks.VERTEL.get(), new ModelFile.UncheckedModelFile(modLoc("block/vertel_block")));
        //blockWithItem(ModBlocks.LAVASH_FURNACE);
        makeCustomCrop((CropBlock) ModBlocks.TOMATO_CROP.get(),"tomato_stage","tomato_stage", TOMATO);
        makeCustomCrop((CropBlock) ModBlocks.CABBAGE_CROP.get(), "cabbage_stage", "cabbage_stage", CABBAGE);
        makeCustomCrop((CropBlock) ModBlocks.CUCUMBER_CROP.get(), "cucumber_stage", "cucumber_stage", CUCUMBER);
    }
    public void makeCustomCrop(CropBlock block, String modelName, String textureName, String type) {
        Function<BlockState, ConfiguredModel[]> function = null;
        switch (type) {
            case TOMATO -> function = state -> tomatoStates(state, block, modelName, textureName);
            case CABBAGE -> function = state -> cabbageStates(state, block, modelName, textureName);
            case CUCUMBER -> function = state -> cucmberStates(state, block, modelName, textureName);
        }
        getVariantBuilder(block).forAllStates(function);
    }


    private ConfiguredModel[] tomatoStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((TomatoCropBlock) block).getAgeProperty()),
                new ResourceLocation(ShawrmaMod.MODID, "block/" + textureName + state.getValue(((TomatoCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }
    private ConfiguredModel[] cabbageStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CabbageCropBlock) block).getAgeProperty()),
                new ResourceLocation(ShawrmaMod.MODID, "block/" + textureName + state.getValue(((CabbageCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private ConfiguredModel[] cucmberStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((CucumberCropBlock) block).getAgeProperty()),
                new ResourceLocation(ShawrmaMod.MODID, "block/" + textureName + state.getValue(((CucumberCropBlock) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
