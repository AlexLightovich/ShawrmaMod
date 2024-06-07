package com.alexlightovich.shawrmamod.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PusherItem extends Item {
    public PusherItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack ret = new ItemStack(this);
        return ret;
    }
}
