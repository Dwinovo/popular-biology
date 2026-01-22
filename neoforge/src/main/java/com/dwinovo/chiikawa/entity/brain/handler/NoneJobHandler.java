package com.dwinovo.chiikawa.entity.brain.handler;

import java.util.List;
import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.utils.BrainUtils;
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
        // Default activity.
        brain.setDefaultActivity(Activity.IDLE);
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        pet.getBrain().setActiveActivityToFirstValid(List.of(Activity.IDLE));
    }
    public static void addCoreTasks(Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
    }

    public static void addIdleTasks(Brain<AbstractPet> brain) {
        BrainUtils.addIdleTasks(brain);
    }
}

