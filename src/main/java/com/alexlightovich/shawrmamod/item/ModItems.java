package com.alexlightovich.shawrmamod.item;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ShawrmaMod.MODID);

    public static final RegistryObject<Item> LAVASH = ITEMS.register("lavash", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                    .saturationMod(0.1f)
                    .nutrition(1)
                    .build())));


    public static final RegistryObject<Item> CABBAGE = ITEMS.register("cabbage", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CABBAGE_SEEDS = ITEMS.register("cabbage_seeds", () -> new ItemNameBlockItem(ModBlocks.CABBAGE_CROP.get(),new Item.Properties()));

    public static final RegistryObject<Item> FLOUR = ITEMS.register("flour", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", () -> new SaltItem(new Item.Properties()));
    public static final RegistryObject<Item> PUSHER = ITEMS.register("pusher", () -> new PusherItem(new Item.Properties()));
    public static final RegistryObject<Item> SAUCE = ITEMS.register("sauce", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds", () -> new ItemNameBlockItem(ModBlocks.CUCUMBER_CROP.get(),new Item.Properties()));

    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DICK = ITEMS.register("dick", () -> new DickItem(new Item.Properties()));
    public static final RegistryObject<Item> TOMATO_SEEDS = ITEMS.register("tomato_seeds", () -> new ItemNameBlockItem(ModBlocks.TOMATO_CROP.get(),new Item.Properties()));
    public static final RegistryObject<Item> RAW_LAVASH = ITEMS.register("raw_lavash", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MEAT_FOR_SHAWRMA = ITEMS.register("meat_shawrma", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .saturationMod(2.5f)
                    .nutrition(2)
                    .meat()
            .build())));
    public static final RegistryObject<Item> KNIFE = ITEMS.register("knife", () -> new KnifeItem());

    public static final RegistryObject<Item> SHAWARMA = ITEMS.register("shawrma", () -> new ShawrmaItem(new Item.Properties()));
    public static final RegistryObject<Item> HALF_SHAWARMA = ITEMS.register("half_shawrma", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                    .saturationMod(7.5f)
                    .nutrition(5)
                    .meat()
                    .build())));


    // public static final RegistryObject<Item> PISKA = ITEMS.register("piska", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
