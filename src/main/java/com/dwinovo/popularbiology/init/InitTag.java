package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public final class InitTag {
    public static final TagKey<Item> ENTITY_FARMER_TOOLS = tag("entity_farmer_tools");
    public static final TagKey<Item> ENTITY_FENCER_TOOLS = tag("entity_fencer_tools");
    public static final TagKey<Item> ENTITY_ARCHER_TOOLS = tag("entity_archer_tools");
    public static final TagKey<EntityType<?>> ENTITY_HOSTILE_ENTITY = tagEntity("entity_hostile_entity");

    private InitTag() {
    }

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, name));
    }

    private static TagKey<EntityType<?>> tagEntity(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, name));
    }
}
