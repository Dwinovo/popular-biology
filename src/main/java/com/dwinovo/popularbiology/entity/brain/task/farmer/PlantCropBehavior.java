package com.dwinovo.popularbiology.entity.brain.task.farmer;


import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.utils.Utils;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

import net.minecraft.server.level.ServerLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
// 这个类用于种植作物
public class PlantCropBehavior extends Behavior<AbstractPet>{
   
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.PLANT_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    /**
     * 这个函数用于初始化任务
     */
    public PlantCropBehavior() {
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
        // 只有当处于工作模式，职业为农民，CropPos不存在，PlantPos存在
        if(
            pet.getPetMode() == PetMode.WORK && 
            pet.getPetJobId() == InitRegistry.FARMER_ID && 
            !pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent() && 
            pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent() 
        )
        {
            // 获取耕地的位置
            BlockPos farmlandPos = pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).get();
            // 距离耕地的位置2格并且再检测是否可以种植作物
            if( 
                // 距离耕地的位置2格
                pet.distanceToSqr(Vec3.atCenterOf(farmlandPos)) <= 2.0D
                // 可以种植作物
                && Utils.isCanPlantFarmland(world, farmlandPos)
            ){
                return true;
            }
        }
        // 否则返回false
        return false;
    }
    @Override
    protected boolean canStillUse(ServerLevel world, AbstractPet pet, long time) {
        if (!super.canStillUse(world, pet, time)) {
            return false;
        }
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID) {
            return false;
        }
        if (pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return false;
        }
        Optional<BlockPos> farmlandPosOpt = pet.getBrain().getMemory(InitMemory.PLANT_POS.get());
        if (farmlandPosOpt.isEmpty()) {
            return false;
        }
        BlockPos farmlandPos = farmlandPosOpt.get();
        return Utils.isCanPlantFarmland(world, farmlandPos)
            && pet.distanceToSqr(Vec3.atCenterOf(farmlandPos)) <= 2.0D
            && !Utils.getSeed(pet).isEmpty();
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
    /**
     * 这个函数在任务停止的时候调用
     * @param pLevel: 当前世界
     * @param pet: 当前生物
     * @param pGameTime: 当前时间
     */
    @SuppressWarnings("null")
    @Override
    protected void stop(ServerLevel world, AbstractPet pet, long pGameTime) {
        // 获取生物的Brain
        Brain<AbstractPet> brain = pet.getBrain();
        // 安全获取耕地的位置
        Optional<BlockPos> farmlandPosOpt = brain.getMemory(InitMemory.PLANT_POS.get());
        // 如果耕地位置为空
        if(farmlandPosOpt.isEmpty()) return;
        // 获取耕地位置
        BlockPos farmlandPos = farmlandPosOpt.get();
        // 获取种子
        ItemStack seed = Utils.getSeed(pet);
        Optional<BlockState> cropState = getPlantBlockState(seed);
        // 再检测是否可以种植作物
        if (cropState.isPresent() && Utils.isCanPlantFarmland(world, farmlandPos)) {
            // 在耕地上方1格种植作物
            world.setBlock(farmlandPos.above(), cropState.get(), 2);
            // 让宠物背包的种子减少
            seed.shrink(1);
        }
        // 清除耕地位置记忆
        pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
    }

    private static Optional<BlockState> getPlantBlockState(ItemStack seed) {
        if (seed.isEmpty()) {
            return Optional.empty();
        }
        Item item = seed.getItem();
        if (item instanceof BlockItem blockItem) {
            return Optional.of(blockItem.getBlock().defaultBlockState());
        }
        if (item instanceof ItemNameBlockItem nameBlockItem) {
            return Optional.of(nameBlockItem.getBlock().defaultBlockState());
        }
        return Optional.empty();
    }
}
