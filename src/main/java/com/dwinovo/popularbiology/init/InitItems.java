package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, PopularBiology.MODID);

    public static final DeferredHolder<Item, DeferredSpawnEggItem> USAGI_SPAWN_EGG =
            ITEMS.register("usagi_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.USAGI_PET, 0xf28907, 0xF2CB07, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> HACHIWARE_SPAWN_EGG =
            ITEMS.register("hachiware_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.HACHIWARE_PET, 0x00FFFF, 0x89cff0, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> CHIIKAWA_SPAWN_EGG =
            ITEMS.register("chiikawa_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.CHIIKAWA_PET, 0xE7CCCC, 0xEDE8DC, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> SHISA_SPAWN_EGG =
            ITEMS.register("shisa_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.SHISA_PET, 0xFFA500, 0xDFFF00, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> MOMONGA_SPAWN_EGG =
            ITEMS.register("momonga_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.MOMONGA_PET, 0x0ABAB5, 0x00008B, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> KURIMANJU_SPAWN_EGG =
            ITEMS.register("kurimanju_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.KURIMANJU_PET, 0xdac24e, 0xda8b4e, new Item.Properties()));
    public static final DeferredHolder<Item, DeferredSpawnEggItem> RAKKO_SPAWN_EGG =
            ITEMS.register("rakko_spawn_egg",
                    () -> new DeferredSpawnEggItem(InitEntity.RAKKO_PET, 0xeaffd0, 0xeaeaea, new Item.Properties()));

    private InitItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
