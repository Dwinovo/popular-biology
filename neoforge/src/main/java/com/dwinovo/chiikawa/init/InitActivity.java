package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.entity.schedule.Activity;

public final class InitActivity {
    public static final DeferredRegister<Activity> ACTIVITY_TYPES =
        DeferredRegister.create(Registries.ACTIVITY, Chiikawa.MODID);

    // Farmer harvest activity.
    public static final DeferredHolder<Activity, Activity> FARMER_HARVEST =
        ACTIVITY_TYPES.register("farmer_harvest", () -> new Activity("farmer_harvest"));
    // Farmer plant activity.
    public static final DeferredHolder<Activity, Activity> FARMER_PLANT =
        ACTIVITY_TYPES.register("farmer_plant", () -> new Activity("farmer_plant"));
    // Deliver activity.
    public static final DeferredHolder<Activity, Activity> DELEVER =
        ACTIVITY_TYPES.register("delever", () -> new Activity("delever"));

    private InitActivity() {
    }

    // Register activities.
    public static void register(IEventBus eventBus) {
        ACTIVITY_TYPES.register(eventBus);
    }
}


