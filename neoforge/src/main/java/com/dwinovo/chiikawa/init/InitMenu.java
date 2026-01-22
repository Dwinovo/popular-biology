package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.menu.PetBackpackMenu;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class InitMenu {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Chiikawa.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<PetBackpackMenu>> PET_BACKPACK =
            MENUS.register("pet_backpack", () -> new MenuType<>(PetBackpackMenu::new, FeatureFlags.DEFAULT_FLAGS));

    private InitMenu() {
    }

    public static void register(IEventBus modEventBus) {
        MENUS.register(modEventBus);
    }
}


