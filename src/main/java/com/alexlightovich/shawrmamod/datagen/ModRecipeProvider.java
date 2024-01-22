package com.alexlightovich.shawrmamod.datagen;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SHAWARMA.get())
                .requires(ModItems.CABBAGE.get())
                .requires(ModItems.TOMATO.get())
                .requires(ModItems.LAVASH.get())
                .requires(ModItems.SAUCE.get())
                .requires(ModItems.CUCUMBER.get())
                .requires(Items.COOKED_CHICKEN)
                .unlockedBy(getHasName(ModItems.CUCUMBER.get()), has(ModItems.CUCUMBER.get()))
                .save(recipeOutput);
    }
}
