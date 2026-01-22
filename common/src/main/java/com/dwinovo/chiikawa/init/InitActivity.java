package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.platform.Services;
import java.util.function.Supplier;
import net.minecraft.world.entity.schedule.Activity;

public final class InitActivity {
    // Farmer harvest activity.
    public static final Supplier<Activity> FARMER_HARVEST =
        Services.PLATFORM_REGISTRY.farmerHarvestActivity();
    // Farmer plant activity.
    public static final Supplier<Activity> FARMER_PLANT =
        Services.PLATFORM_REGISTRY.farmerPlantActivity();
    // Deliver activity.
    public static final Supplier<Activity> DELEVER =
        Services.PLATFORM_REGISTRY.deleverActivity();

    private InitActivity() {
    }

    public static void init() {
    }
}
