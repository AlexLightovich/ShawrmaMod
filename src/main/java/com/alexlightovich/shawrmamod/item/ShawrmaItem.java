package com.alexlightovich.shawrmamod.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShawrmaItem extends Item {

    //.effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,100,100),1f)

    public ShawrmaItem(Properties p_41383_) {
        super(p_41383_.food(new FoodProperties.Builder()
                .saturationMod(7.5f)
                .nutrition(5)
                .meat()
                .build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        if(isEdible()) {
            p_41411_.eat(p_41410_, p_41409_);
            p_41411_.setItemInHand(p_41411_.getUsedItemHand(),new ItemStack(ModItems.HALF_SHAWARMA.get()));
            return p_41409_;
        } else {
            return p_41409_;
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (itemstack.isEdible()) {
            if (p_41433_.canEat(itemstack.getFoodProperties(p_41433_).canAlwaysEat())) {
                p_41433_.startUsingItem(p_41434_);
                return InteractionResultHolder.consume(itemstack);
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            return InteractionResultHolder.pass(p_41433_.getItemInHand(p_41434_));
        }
    }
}
