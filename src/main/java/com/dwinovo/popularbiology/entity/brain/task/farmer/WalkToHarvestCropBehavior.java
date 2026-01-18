package com.dwinovo.popularbiology.entity.brain.task.farmer;


import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;

import net.minecraft.server.level.ServerLevel;

import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.utils.Utils;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;
// 这个任务用于走到作物旁边
public class WalkToHarvestCropBehavior extends Behavior<AbstractPet>{
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.HARVEST_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    // 速度
    private final float speed;
    /**
     * 这个函数用于初始化任务
     * @param speed: 速度
     */
    public WalkToHarvestCropBehavior(float speed) {
        // 初始化任务，超时时间15tick
        super(REQUIRED_MEMORIES, 15);
        // 设置速度
        this.speed = speed;
    }
    /**
     * 这个函数用于检查是否可以开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @return: 是否可以开始任务
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        // 处于工作模式，职业为农民，HarvestPos不为空
        if (pet.getPetMode() == PetMode.WORK && pet.getPetJobId() == InitRegistry.FARMER_ID 
            && pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()
        ){
            net.minecraft.core.BlockPos target = pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).get();
            if (Utils.canHarvesr(world, target) && Utils.canReach(pet, target)) {
                return true;
            }
            pet.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
        }
        // 否则不可以开始任务
        return false;
    }
    /**
     * 这个函数用于开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @param time: 当前时间
     */
    @Override
    protected void start(ServerLevel world, AbstractPet pet, long time) {
        BehaviorUtils.setWalkAndLookTargetMemories(pet, pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).get(), speed, 0);
    }
}
