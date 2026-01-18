package com.dwinovo.popularbiology.entity.brain.handler;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.brain.task.farmer.DeliverCropTask;
import com.dwinovo.popularbiology.entity.brain.task.farmer.HarvestCropBehavior;
import com.dwinovo.popularbiology.entity.brain.task.farmer.PlantCropTask;
import com.dwinovo.popularbiology.entity.brain.task.farmer.WalkToContainerTask;
import com.dwinovo.popularbiology.entity.brain.task.farmer.WalkToHarvestCropTask;
import com.dwinovo.popularbiology.entity.brain.task.farmer.WalkToPlantCropTask;
import com.dwinovo.popularbiology.init.InitActivity;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.utils.BrainUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;

public final class FarmerJobHandler {
    private FarmerJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
        BrainUtils.addIdleTasks(brain);
        // 添加收获任务
        addFarmHarvestActivity(brain);
        // 添加种植任务
        addFarmPlantActivity(brain);
        // 添加传递任务
        addDeliverActivity(brain,pet);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        
        
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        updateActivity(brain);
    }

    private static void updateActivity(Brain<AbstractPet> brain) {
        Activity preferred = getPreferredActivity(brain);
        if (brain.isActive(preferred)) {
            return;
        }
        ImmutableList.Builder<Activity> activities = ImmutableList.builder();   
        //优先级：
        //1.FARM_HARVEST
        //必须HarvestPos存在时
        if(brain.getMemory(InitMemory.HARVEST_POS.get()).isPresent())
        {
            activities.add(InitActivity.FARMER_HARVEST.get());
        }
        //2.FARM_PLANT
        //必须PlantPos存在时
        if(brain.getMemory(InitMemory.PLANT_POS.get()).isPresent())
        {
            activities.add(InitActivity.FARMER_PLANT.get());
        }
        //3.DELEVER
        //必须ContainerPos存在时
        if(brain.getMemory(InitMemory.CONTAINER_POS.get()).isPresent())
        {
            activities.add(InitActivity.DELEVER.get());
        }
        //4.IDLE
        activities.add(Activity.IDLE);
        //实时更新优先级高的活动
        brain.setActiveActivityToFirstValid(activities.build());
    }

    private static Activity getPreferredActivity(Brain<AbstractPet> brain) {
        if (brain.getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return InitActivity.FARMER_HARVEST.get();
        }
        if (brain.getMemory(InitMemory.PLANT_POS.get()).isPresent()) {
            return InitActivity.FARMER_PLANT.get();
        }
        if (brain.getMemory(InitMemory.CONTAINER_POS.get()).isPresent()) {
            return InitActivity.DELEVER.get();
        }
        return Activity.IDLE;
    }
    
    private static void addFarmHarvestActivity(Brain<AbstractPet> brain) {
        // 添加收获任务
        brain.addActivity(InitActivity.FARMER_HARVEST.get(), ImmutableList.of(
            // 收获任务
            Pair.of(3, new HarvestCropBehavior()),
            // 移动到收获任务
            Pair.of(4, new WalkToHarvestCropTask(0.8F))
        ));
    }
    private static void addFarmPlantActivity(Brain<AbstractPet> brain) {
        // 添加种植任务
        brain.addActivity(InitActivity.FARMER_PLANT.get(), ImmutableList.of(
            // 种植任务
            Pair.of(3, new PlantCropTask()),
            // 移动到种植任务
            Pair.of(4, new WalkToPlantCropTask(0.8F))
        ));
    }
    private static void addDeliverActivity(Brain<AbstractPet> brain,AbstractPet pet) {
        // 添加传递任务
        brain.addActivity(InitActivity.DELEVER.get(), ImmutableList.of(
            // 传递任务
            Pair.of(3, new DeliverCropTask()),
            // 移动到传递任务
            Pair.of(4, new WalkToContainerTask(0.8F))
        ));
    }
}
