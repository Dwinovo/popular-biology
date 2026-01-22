package com.dwinovo.chiikawa.platform.services;

import com.dwinovo.chiikawa.entity.brain.sensor.PetAttackbleEntitySensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetContainerSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetHarvestCropSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetPickableItemSensor;
import com.dwinovo.chiikawa.entity.brain.sensor.PetPlantCropSensor;
import com.dwinovo.chiikawa.menu.PetBackpackMenu;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.inventory.MenuType;

public interface IPlatformRegistryAccess {
    Supplier<SensorType<PetAttackbleEntitySensor>> petAttackbleEntitySensor();

    Supplier<SensorType<PetHarvestCropSensor>> petHarvestCropSensor();

    Supplier<SensorType<PetPlantCropSensor>> petPlantCropSensor();

    Supplier<SensorType<PetContainerSensor>> petContainerSensor();

    Supplier<SensorType<PetPickableItemSensor>> petItemEntitySensor();

    Supplier<Activity> farmerHarvestActivity();

    Supplier<Activity> farmerPlantActivity();

    Supplier<Activity> deleverActivity();

    Supplier<MenuType<PetBackpackMenu>> petBackpackMenu();
}
