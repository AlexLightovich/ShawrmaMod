package com.alexlightovich.shawrmamod.item;

import net.minecraft.world.item.*;

public class KnifeItem extends SwordItem {

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack ret = new ItemStack(this);
        return ret;
    }

    public KnifeItem() {
        super(Tiers.WOOD, 1, 1, new Item.Properties());
    }
}
