package com.dwinovo.popularbiology.entity.brain.task.farmer;


import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import java.util.Optional;
import com.dwinovo.popularbiology.utils.Utils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
// 这个类用于采集作物
public class HarvestCropBehavior extends Behavior<AbstractPet>{
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.HARVEST_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    /**
     * 这个函数用于初始化任务
     */
    public HarvestCropBehavior() {
        super(REQUIRED_MEMORIES, 100);
    }   
    /**
     * 这个函数用于检查是否可以开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @return: 是否可以开始任务
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        // 只有当处于工作模式，职业为农民,CropPos存在，并且距离作物位置2格并且再检测是否可以采集作物才能开始
        if(
            pet.getPetMode() == PetMode.WORK && 
            pet.getPetJobId() == InitRegistry.FARMER_ID && 
            pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent() 
        )
        {
            // 获取生物的Brain 
            Brain<AbstractPet> brain = pet.getBrain();
            // 获取作物位置
            BlockPos cropPos = brain.getMemory(InitMemory.HARVEST_POS.get()).get();
            // 距离作物位置1格并且再检测是否可以种植作物并且处于工作模式
            return Utils.canHarvesr(world, cropPos) 
            // 处于工作模式
            && pet.getPetMode() == PetMode.WORK
            && pet.distanceToSqr(Vec3.atCenterOf(cropPos)) <= 1.0D;
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
    }
    @Override
    protected boolean canStillUse(ServerLevel world, AbstractPet pet, long time) {
        if (!super.canStillUse(world, pet, time)) {
            return false;
        }
        Optional<BlockPos> cropPosOpt = pet.getBrain().getMemory(InitMemory.HARVEST_POS.get());
        if (cropPosOpt.isEmpty()) {
            return false;
        }
        BlockPos cropPos = cropPosOpt.get();
        return Utils.canHarvesr(world, cropPos)
            && pet.distanceToSqr(Vec3.atCenterOf(cropPos)) <= 1.0D
            && pet.getPetMode() == PetMode.WORK
            && pet.getPetJobId() == InitRegistry.FARMER_ID;
    }
    
    /**
     * 这个函数在任务停止的时候调用
     * @param pLevel: 当前世界
     * @param pet: 当前生物
     * @param pGameTime: 当前时间
     */
    @Override
    protected void stop(ServerLevel world, AbstractPet pet, long pGameTime) {
        // 获取生物的Brain  
        Brain<AbstractPet> brain = pet.getBrain();
        // 安全获取作物位置
        Optional<BlockPos> cropPosOpt = brain.getMemory(InitMemory.HARVEST_POS.get());
        // 如果作物位置为空
        if(cropPosOpt.isEmpty()) return;
        // 获取作物位置
        BlockPos cropPos = cropPosOpt.get();

        if (!world.isClientSide()) {
            ItemStack tool = pet.getMainHandItem();
            // 检查作物是否成熟
            if (Utils.canHarvesr(world, cropPos)) {
                pet.triggerAnim("main", "use_mainhand");
                world.destroyBlock(cropPos, true, pet);
                // 损坏工具
                tool.hurtAndBreak(1, pet, EquipmentSlot.MAINHAND);
            }
        }
        // 清除作物位置记忆
        pet.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
    }
}
    
    


