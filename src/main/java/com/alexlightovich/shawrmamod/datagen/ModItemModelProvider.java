package com.alexlightovich.shawrmamod.datagen;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ShawrmaMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleItem(ModItems.CABBAGE);
        simpleItem(ModItems.CABBAGE_SEEDS);
        simpleItem(ModItems.TOMATO);
        simpleItem(ModItems.TOMATO_SEEDS);
        simpleItem(ModItems.LAVASH);
        simpleItem(ModItems.RAW_LAVASH);
        simpleItem(ModItems.CUCUMBER);
        simpleItem(ModItems.CUCUMBER_SEEDS);
        simpleItem(ModItems.SAUCE);
        simpleItem(ModItems.KNIFE);
        simpleItem(ModItems.PUSHER);
        simpleItem(ModItems.MEAT_FOR_SHAWRMA);
        simpleItem(ModItems.DICK);
        simpleItem(ModItems.FLOUR);
        simpleItem(ModItems.SALT);
    }
//    private ItemModelBuilder modelItem(RegistryObject<Item> item) {
//        return withExistingParent(item)
//    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ShawrmaMod.MODID,"item/" + item.getId().getPath()));
    }
}
