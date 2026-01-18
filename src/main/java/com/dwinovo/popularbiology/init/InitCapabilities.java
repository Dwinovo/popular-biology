package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.entity.AbstractPet;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public final class InitCapabilities {
    private InitCapabilities() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(InitCapabilities::registerCapabilities);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        registerPet(event, InitEntity.USAGI_PET.get());
        registerPet(event, InitEntity.HACHIWARE_PET.get());
        registerPet(event, InitEntity.CHIIKAWA_PET.get());
        registerPet(event, InitEntity.SHISA_PET.get());
        registerPet(event, InitEntity.MOMONGA_PET.get());
        registerPet(event, InitEntity.KURIMANJU_PET.get());
        registerPet(event, InitEntity.RAKKO_PET.get());
    }

    private static <T extends AbstractPet> void registerPet(RegisterCapabilitiesEvent event, EntityType<T> type) {
        event.registerEntity(Capabilities.ItemHandler.ENTITY, type, (pet, context) -> pet.getBackpackHandler());
        event.registerEntity(Capabilities.ItemHandler.ENTITY_AUTOMATION, type, (pet, context) -> pet.getBackpackHandler());
    }
}
