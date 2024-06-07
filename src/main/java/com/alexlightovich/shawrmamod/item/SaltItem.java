package com.alexlightovich.shawrmamod.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SaltItem extends Item {

    @Override
    public void onCraftedBy(ItemStack p_41447_, Level p_41448_, Player p_41449_) {
        //super.onCraftedBy(p_41447_, p_41448_, p_41449_);
        p_41449_.getInventory().add(new ItemStack(Items.BUCKET));
    }

    public SaltItem(Properties p_41383_) {
        super(p_41383_);
    }
}
