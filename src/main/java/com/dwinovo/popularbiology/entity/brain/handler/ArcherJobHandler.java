package com.dwinovo.popularbiology.entity.brain.handler;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.brain.task.archer.HurtRangedAttackTargetTask;
import com.dwinovo.popularbiology.utils.BrainUtils;
import com.dwinovo.popularbiology.utils.Utils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

public final class ArcherJobHandler {
    private ArcherJobHandler() {
    }

    public static void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        BrainUtils.addCoreTasks(brain);
        BrainUtils.addIdleTasks(brain);

        addFencerTasks(brain);
        // 设置核心活动
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        // 设置默认活动
        brain.setDefaultActivity(Activity.IDLE);
    }

    public static void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        if (pet.getPetMode() != com.dwinovo.popularbiology.entity.PetMode.WORK) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
            brain.eraseMemory(MemoryModuleType.ATTACK_COOLING_DOWN);
            
            return;
        }
        ImmutableList.Builder<Activity> activities = ImmutableList.builder();
        // 执行WORK必须要有AttackTarget并且有箭并且没有冷却
        if (brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
        && !Utils.getArrow(pet).isEmpty()
        && !brain.hasMemoryValue(MemoryModuleType.ATTACK_COOLING_DOWN))
        {
            activities.add(Activity.WORK);
        }
        //2.IDLE
        activities.add(Activity.IDLE);
        //实时更新优先级高的活动
        brain.setActiveActivityToFirstValid(activities.build());
        
    }
    public static void addFencerTasks(Brain<AbstractPet> brain){
        Pair<Integer, BehaviorControl<? super AbstractPet>> hurtRangedAttackTarget = Pair.of(4, new HurtRangedAttackTargetTask());
        brain.addActivity(Activity.WORK, ImmutableList.of(hurtRangedAttackTarget));

    }
}
