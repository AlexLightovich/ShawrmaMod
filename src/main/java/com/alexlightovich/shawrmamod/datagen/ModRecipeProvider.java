package com.alexlightovich.shawrmamod.datagen;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.alexlightovich.shawrmamod.block.ModBlocks;
import com.alexlightovich.shawrmamod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.util.datafix.fixes.FurnaceRecipeFix;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
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
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.WATER_BUCKET),
                RecipeCategory.FOOD,
                ModItems.SALT.get(),10f,300)
                .unlockedBy(getHasName(Items.WATER_BUCKET),has(Items.WATER_BUCKET))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SHAWARMA.get())
                .requires(ModItems.CABBAGE.get())
                .requires(ModItems.TOMATO.get())
                .requires(ModItems.LAVASH.get())
                .requires(ModItems.SAUCE.get())
                .requires(ModItems.CUCUMBER.get())
                .requires(ModItems.MEAT_FOR_SHAWRMA.get())
                .unlockedBy(getHasName(ModItems.CUCUMBER.get()), has(ModItems.CUCUMBER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HALF_SHAWARMA.get(),2)
                .requires(ModItems.SHAWARMA.get())
                .requires(ModItems.KNIFE.get())
                .unlockedBy(getHasName(ModItems.HALF_SHAWARMA.get()), has(ModItems.HALF_SHAWARMA.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FLOUR.get(),2)
                .requires(Items.WHEAT)
                .requires(ModItems.PUSHER.get())
                .unlockedBy(getHasName(ModItems.PUSHER.get()), has(ModItems.PUSHER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_LAVASH.get(),1)
                .requires(Items.WATER_BUCKET)
                .requires(ModItems.SALT.get())
                .requires(ModItems.FLOUR.get())
                .unlockedBy(getHasName(ModItems.PUSHER.get()), has(ModItems.PUSHER.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MEAT_FOR_SHAWRMA.get(),1)
                .requires(Items.COOKED_CHICKEN)
                .requires(ModItems.KNIFE.get())
                .unlockedBy(getHasName(ModItems.KNIFE.get()), has(ModItems.KNIFE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAUCE.get(),1)
                .requires(ModItems.TOMATO.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.PUSHER.get())
                .unlockedBy(getHasName(ModItems.PUSHER.get()), has(ModItems.PUSHER.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KNIFE.get())
                .pattern("II ")
                .pattern("II ")
                .pattern("S  ")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.STICK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VERTEL.get())
                .pattern("III")
                .pattern("CBC")
                .pattern("CCC")
                .define('I', Items.IRON_INGOT)
                .define('C', Items.COBBLESTONE)
                .define('B', Items.IRON_BARS)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PUSHER.get())
                .pattern(" I ")
                .pattern(" I ")
                .pattern("III")
                .define('I', Items.OAK_PLANKS)
                .unlockedBy(getHasName(Items.OAK_PLANKS), has(Items.OAK_PLANKS))
                .save(recipeOutput);
    }

}
