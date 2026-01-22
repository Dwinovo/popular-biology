package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.menu.PetBackpackMenu;
import com.dwinovo.chiikawa.platform.Services;
import java.util.function.Supplier;
import net.minecraft.world.inventory.MenuType;

public final class InitMenu {
    public static final Supplier<MenuType<PetBackpackMenu>> PET_BACKPACK =
        Services.PLATFORM_REGISTRY.petBackpackMenu();

    private InitMenu() {
    }

    public static void init() {
    }
}
