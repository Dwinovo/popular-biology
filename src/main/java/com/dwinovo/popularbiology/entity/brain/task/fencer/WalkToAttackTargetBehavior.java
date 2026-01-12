package com.dwinovo.popularbiology.entity.brain.task.fencer;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class WalkToAttackTargetBehavior extends Behavior<AbstractPet>{
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT
    );
    public WalkToAttackTargetBehavior(){
        super(REQUIRED_MEMORIES, 20);
    }
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        /*
         * 1.是WORK状态
         * 2.是Fencer职业
         * 3.存在AttackTarget
         * 4.Action为0
         */
        return pet.getPetMode() == PetMode.WORK 
        && pet.getPetJobId() == 2 
        && pet.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent();
    }
    @Override
    protected void tick(ServerLevel level, AbstractPet pet, long gameTime) {
        // 持续走向攻击目标
        LivingEntity target = pet.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
        BehaviorUtils.setWalkAndLookTargetMemories(pet, target, 0.9F, 1);
    }
    @Override
    protected boolean canStillUse(ServerLevel level, AbstractPet entity, long gameTime) {
        // 和初始条件一样
        return checkExtraStartConditions(level, entity);
    }
    
}
