package com.dwinovo.popularbiology.entity.brain.handler;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.brain.task.fencer.WalkToAttackTargetBehavior;
import com.dwinovo.popularbiology.utils.BrainUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

public final class FencerJobHandler {
    private FencerJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
        BrainUtils.addIdleTasks(brain);
        addFencerTasks(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        updateActivity(brain);
    }

    private static void updateActivity(Brain<AbstractPet> brain) {
        ImmutableList.Builder<Activity> activities = ImmutableList.builder();
        // 优先级：
        // 1. WORK
        // 必须AttackTarget存在时，并且没有冷却
        if (brain.getMemory(MemoryModuleType.ATTACK_TARGET).isPresent()
            && !brain.getMemory(MemoryModuleType.ATTACK_COOLING_DOWN).isPresent()) {
            activities.add(Activity.WORK);
        }
        // 2. IDLE
        activities.add(Activity.IDLE);
        // 实时更新优先级高的活动
        brain.setActiveActivityToFirstValid(activities.build());
    }
    private static void addFencerTasks(Brain<AbstractPet> brain) {
        // 添加攻击任务
        //Pair<Integer, BehaviorControl<? super AbstractPet>> hurtAttackTarget = Pair.of(4, new HurtAttackTargetTask());
        // 添加攻击任务
        Pair<Integer, BehaviorControl<? super AbstractPet>> walkToAttackTarget = Pair.of(5, new WalkToAttackTargetBehavior());
        // 添加攻击任务
        brain.addActivity(Activity.WORK, ImmutableList.of(walkToAttackTarget));
    }
}
