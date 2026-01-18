package com.dwinovo.popularbiology.entity.brain.task.farmer;




import net.minecraft.world.entity.ai.behavior.Behavior;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.utils.Utils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
// 这个任务用于走到种植作物的耕地
public class WalkToPlantCropBehavior extends Behavior<AbstractPet>{
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.PLANT_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    // 速度
    private final float speed;
    /**
     * 这个函数用于初始化任务
     * @param speed: 速度
     */
    public WalkToPlantCropBehavior(float speed) {
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
        //处于工作模式，职业为农民，HarvestPos不存在，背包里面有种子，PlantPos存在
        if(
            pet.getPetMode() == PetMode.WORK && 
            pet.getPetJobId() == InitRegistry.FARMER_ID && 
            !pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent() && 
            !Utils.getSeed(pet).isEmpty() && 
            pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()
        ){
            net.minecraft.core.BlockPos target = pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).get();
            if (Utils.isCanPlantFarmland(world, target) && Utils.canReach(pet, target)) {
                return true;
            }
            pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
        }
        // 否则返回false
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
        BehaviorUtils.setWalkAndLookTargetMemories(pet, pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).get(), speed, 0);
    }
    
}

