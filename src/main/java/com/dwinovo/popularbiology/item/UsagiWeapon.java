package com.dwinovo.popularbiology.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class UsagiWeapon extends SwordItem {
    public UsagiWeapon() {
        super(Tiers.IRON,
                new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F)));
    }
}
