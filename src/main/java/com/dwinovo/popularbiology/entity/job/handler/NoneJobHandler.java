package com.dwinovo.popularbiology.entity.job.handler;

import java.util.List;
import java.util.Set;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.PopularBiology;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.schedule.Activity;

public final class NoneJobHandler {
    private NoneJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        addCoreTasks(brain);
        addIdleTasks(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        // 设置默认活动
        brain.setDefaultActivity(Activity.IDLE);
    }

    public static void tickBrain(AbstractPet pet) {
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

        Pair<Integer, BehaviorControl<? super AbstractPet>> walkToTarget = Pair.of(1, new MoveToTargetSink());
        Pair<Integer, BehaviorControl<? super AbstractPet>> look = Pair.of(0, new LookAtTargetSink(45, 90));

        brain.addActivity(Activity.CORE, ImmutableList.of(look, walkToTarget));
    }
    public static void addIdleTasks(Brain<AbstractPet> brain) {
        Pair<Integer, BehaviorControl<? super AbstractPet>> randomStroll = Pair.of(1, RandomStroll.stroll(0.6F));
        brain.addActivity(Activity.IDLE, ImmutableList.of(randomStroll));
    }
}
