package com.dwinovo.popularbiology.utils;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.brain.task.tameable.KeepAroundBehavior;
import com.dwinovo.popularbiology.entity.brain.task.tameable.SitBehavior;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.schedule.Activity;

public final class BrainUtils {
    private BrainUtils() {
    }

    public static void addCoreTasks(Brain<AbstractPet> brain) {
        Pair<Integer, BehaviorControl<? super AbstractPet>> sit = Pair.of(0, new SitBehavior<>());
        Pair<Integer, BehaviorControl<? super AbstractPet>> keepAround = Pair.of(2, new KeepAroundBehavior<>(7, 13, 20));
        Pair<Integer, BehaviorControl<? super AbstractPet>> walkToTarget = Pair.of(1, new MoveToTargetSink());
        Pair<Integer, BehaviorControl<? super AbstractPet>> look = Pair.of(0, new LookAtTargetSink(45, 90));
        brain.addActivity(Activity.CORE, ImmutableList.of(look, sit, keepAround, walkToTarget));
    }

    public static void addIdleTasks(Brain<AbstractPet> brain) {
        Pair<Integer, BehaviorControl<? super AbstractPet>> randomTask = Pair.of(99, RandomStroll.stroll(0.6F));
        brain.addActivity(Activity.IDLE, ImmutableList.of(randomTask));
    }
}
