package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chiikawa.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
            CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.chiikawa"))
                    .icon(() -> new ItemStack(InitItems.USAGI_SPAWN_EGG.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(InitItems.USAGI_SPAWN_EGG.get());
                        output.accept(InitItems.HACHIWARE_SPAWN_EGG.get());
                        output.accept(InitItems.CHIIKAWA_SPAWN_EGG.get());
                        output.accept(InitItems.SHISA_SPAWN_EGG.get());
                        output.accept(InitItems.MOMONGA_SPAWN_EGG.get());
                        output.accept(InitItems.KURIMANJU_SPAWN_EGG.get());
                        output.accept(InitItems.RAKKO_SPAWN_EGG.get());
                        output.accept(InitItems.USAGI_WEAPON.get());
                        output.accept(InitItems.HACHIWARE_WEAPON.get());
                        output.accept(InitItems.CHIIKAWA_WEAPON.get());
                    })
                    .build());

    private InitTabs() {
    }

    public static void register(IEventBus modEventBus) {
        CREATIVE_TABS.register(modEventBus);
    }
}


