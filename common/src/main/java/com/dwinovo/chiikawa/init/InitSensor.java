package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.entity.brain.sensor.PetAttackbleEntitySensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetContainerSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetHarvestCropSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetPickableItemSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetPlantCropSensor;
import com.dwinovo.chiikawa.platform.Services;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.sensing.SensorType;

public final class InitSensor {
    public static final Supplier<SensorType<PetAttackbleEntitySensor>> PET_ATTACKBLE_ENTITY_SENSOR =
        Services.PLATFORM_REGISTRY.petAttackbleEntitySensor();
    public static final Supplier<SensorType<PetHarvestCropSensor>> PET_HARVEST_CROP_SENSOR =
        Services.PLATFORM_REGISTRY.petHarvestCropSensor();
    public static final Supplier<SensorType<PetPlantCropSensor>> PET_PLANT_CROP_SENSOR =
        Services.PLATFORM_REGISTRY.petPlantCropSensor();
    public static final Supplier<SensorType<PetContainerSensor>> PET_CONTAINER_SENSOR =
        Services.PLATFORM_REGISTRY.petContainerSensor();
    public static final Supplier<SensorType<PetPickableItemSensor>> PET_ITEM_ENTITY_SENSOR =
        Services.PLATFORM_REGISTRY.petItemEntitySensor();

    private InitSensor() {
    }

    public static void init() {
    }
}
