package com.alexlightovich.shawrmamod.item;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
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

    public static final RegistryObject<Item> SAUCE = ITEMS.register("sauce", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TOMATO = ITEMS.register("tomato", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHAWARMA = ITEMS.register("shawrma", () -> new Item(new Item.Properties().food(
            new FoodProperties.Builder()
                    .saturationMod(15f)
                    .nutrition(10)
                    .meat()
                    .build()
            )));


    // public static final RegistryObject<Item> PISKA = ITEMS.register("piska", () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
