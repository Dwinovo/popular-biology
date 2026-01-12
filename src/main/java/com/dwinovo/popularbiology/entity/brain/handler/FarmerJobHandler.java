package com.dwinovo.popularbiology.entity.brain.handler;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.utils.BrainUtils;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;

public final class FarmerJobHandler {
    private FarmerJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
        BrainUtils.addIdleTasks(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        // TODO: add farming activities later
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        // TODO: select activity later
    }
}
