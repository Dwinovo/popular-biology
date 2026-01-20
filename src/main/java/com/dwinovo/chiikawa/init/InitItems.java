package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.item.ChiikawaWeapon;
import com.dwinovo.chiikawa.item.HachiwareWeapon;
import com.dwinovo.chiikawa.item.UsagiWeapon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

public final class InitItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Chiikawa.MODID);

    public static final DeferredItem<SpawnEggItem> USAGI_SPAWN_EGG =
            ITEMS.registerItem("usagi_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.USAGI_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> HACHIWARE_SPAWN_EGG =
            ITEMS.registerItem("hachiware_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.HACHIWARE_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> CHIIKAWA_SPAWN_EGG =
            ITEMS.registerItem("chiikawa_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.CHIIKAWA_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> SHISA_SPAWN_EGG =
            ITEMS.registerItem("shisa_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.SHISA_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> MOMONGA_SPAWN_EGG =
            ITEMS.registerItem("momonga_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.MOMONGA_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> KURIMANJU_SPAWN_EGG =
            ITEMS.registerItem("kurimanju_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.KURIMANJU_PET.get(), properties));
    public static final DeferredItem<SpawnEggItem> RAKKO_SPAWN_EGG =
            ITEMS.registerItem("rakko_spawn_egg",
                    properties -> new SpawnEggItem(InitEntity.RAKKO_PET.get(), properties));
    public static final DeferredItem<Item> USAGI_WEAPON =
            ITEMS.registerItem("usagi_weapon", UsagiWeapon::new, new Item.Properties());
    public static final DeferredItem<Item> HACHIWARE_WEAPON =
            ITEMS.registerItem("hachiware_weapon", HachiwareWeapon::new, new Item.Properties());
    public static final DeferredItem<Item> CHIIKAWA_WEAPON =
            ITEMS.registerItem("chiikawa_weapon", ChiikawaWeapon::new, new Item.Properties());

    private InitItems() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
