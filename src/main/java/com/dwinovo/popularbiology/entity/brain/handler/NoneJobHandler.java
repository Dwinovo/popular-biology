package com.dwinovo.popularbiology.entity.brain.handler;

import java.util.List;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.utils.BrainUtils;
import com.dwinovo.popularbiology.PopularBiology;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;

public final class NoneJobHandler {
    private NoneJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
        BrainUtils.addIdleTasks(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        // 设置默认活动
        brain.setDefaultActivity(Activity.IDLE);
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        pet.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
        if (pet.tickCount % 40 == 0) {
            PopularBiology.LOGGER.debug(
                "[PetBrain] {} core={} idle={} work={}",
                pet.getStringUUID(),
                pet.getBrain().isActive(Activity.CORE),
                pet.getBrain().isActive(Activity.IDLE),
                pet.getBrain().isActive(Activity.WORK)
            );
        }
    }
    public static void addCoreTasks(Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
    }

    public static void addIdleTasks(Brain<AbstractPet> brain) {
        BrainUtils.addIdleTasks(brain);
    }
}
