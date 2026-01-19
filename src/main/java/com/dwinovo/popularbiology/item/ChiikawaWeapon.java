package com.dwinovo.popularbiology.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class ChiikawaWeapon extends SwordItem {
    public ChiikawaWeapon() {
        super(Tiers.STONE,
                new Item.Properties().attributes(SwordItem.createAttributes(Tiers.STONE, 3, -2.4F)));
    }
}
