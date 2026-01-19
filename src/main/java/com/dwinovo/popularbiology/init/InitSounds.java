package com.dwinovo.popularbiology.init;

import java.util.ArrayList;
import java.util.List;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, PopularBiology.MODID);
    private static final List<SoundEntry> ENTRIES = new ArrayList<>();

    public static final DeferredHolder<SoundEvent, SoundEvent> CHIIKAWA_INJURED = register("chiikawa/injured");
    public static final DeferredHolder<SoundEvent, SoundEvent> CHIIKAWA_TAME = register("chiikawa/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> HACHIWARE_INJURED = register("hachiware/injured");
    public static final DeferredHolder<SoundEvent, SoundEvent> HACHIWARE_TAME = register("hachiware/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> KURIMANJU_TAME = register("kurimanju/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> MOMONGA_INJURED = register("momonga/injured");
    public static final DeferredHolder<SoundEvent, SoundEvent> MOMONGA_TAME = register("momonga/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> RAKKO_TAME = register("rakko/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> SHISA_TAME = register("shisa/tame");

    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_AMBIENT = register("usagi/ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_ATTACK = register("usagi/attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_INJURED = register("usagi/injured");
    public static final DeferredHolder<SoundEvent, SoundEvent> USAGI_TAME = register("usagi/tame");

    private InitSounds() {
    }

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }

    public static List<SoundEntry> entries() {
        return List.copyOf(ENTRIES);
    }

    private static DeferredHolder<SoundEvent, SoundEvent> register(String path) {
        DeferredHolder<SoundEvent, SoundEvent> holder = SOUND_EVENTS.register(path,
                () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, path)));
        ENTRIES.add(new SoundEntry(path, holder));
        return holder;
    }

    public record SoundEntry(String path, DeferredHolder<SoundEvent, SoundEvent> holder) {
    }
}
