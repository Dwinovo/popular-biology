package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.brain.sensor.PetAttackbleEntitySensor;
import com.dwinovo.popularbiology.entity.brain.sensor.PetContainerSensor;
import com.dwinovo.popularbiology.entity.brain.sensor.PetHarvestCropSensor;
import com.dwinovo.popularbiology.entity.brain.sensor.PetPickableItemSensor;
import com.dwinovo.popularbiology.entity.brain.sensor.PetPlantCropSensor;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitSensor {
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
        DeferredRegister.create(Registries.SENSOR_TYPE, PopularBiology.MODID);

    public static final DeferredHolder<SensorType<?>, SensorType<PetAttackbleEntitySensor>> PET_ATTACKBLE_ENTITY_SENSOR =
        SENSOR_TYPES.register("pet_attackble_entity_sensor", () -> new SensorType<>(PetAttackbleEntitySensor::new));
    public static final DeferredHolder<SensorType<?>, SensorType<PetHarvestCropSensor>> PET_HARVEST_CROP_SENSOR =
        SENSOR_TYPES.register("pet_harvest_crop_sensor", () -> new SensorType<>(PetHarvestCropSensor::new));
    public static final DeferredHolder<SensorType<?>, SensorType<PetPlantCropSensor>> PET_PLANT_CROP_SENSOR =
        SENSOR_TYPES.register("pet_plant_crop_sensor", () -> new SensorType<>(PetPlantCropSensor::new));
    public static final DeferredHolder<SensorType<?>, SensorType<PetContainerSensor>> PET_CONTAINER_SENSOR =
        SENSOR_TYPES.register("pet_container_sensor", () -> new SensorType<>(PetContainerSensor::new));
    public static final DeferredHolder<SensorType<?>, SensorType<PetPickableItemSensor>> PET_ITEM_ENTITY_SENSOR =
        SENSOR_TYPES.register("pet_item_entity_sensor", () -> new SensorType<>(PetPickableItemSensor::new));

    private InitSensor() {
    }

    public static void register(IEventBus modEventBus) {
        SENSOR_TYPES.register(modEventBus);
    }
}
