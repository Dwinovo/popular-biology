package com.dwinovo.chiikawa.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class HachiwareWeapon extends Item {
    public HachiwareWeapon(Item.Properties properties) {
        super(properties.sword(ToolMaterial.STONE, 3.0F, -2.4F));
    }
}

