package com.dwinovo.popularbiology.entity.brain.task.tameable;

import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.minecraft.world.item.ItemStack;
// 这个任务用于拾取物品
public class PickUpItemTask extends Behavior<AbstractPet> {
    // 速度
    private final float speedModifier;
    private static final double PICKUP_DISTANCE_SQ = 2.25D;
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.PICKABLE_ITEM.get(), MemoryStatus.VALUE_PRESENT
    );
    /**
     * 这个函数用于初始化任务
     * @param speedModifier: 速度
     */
    public PickUpItemTask(float speedModifier) {
        // 初始化任务
        super(REQUIRED_MEMORIES, 10);
        // 设置速度
        this.speedModifier = speedModifier;
    }
    /**
     * 这个函数用于检查是否可以开始任务
     * @param level: 当前世界
     * @param entity: 当前生物
     * @return: 是否可以开始任务
     */
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, AbstractPet entity) {
        // 如果生物被驯化并且处于工作状态，则可以开始任务
        return entity.isTame() && entity.getPetMode() == PetMode.WORK;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, AbstractPet entity, long time) {
        return entity.isTame()
            && entity.getPetMode() == PetMode.WORK
            && entity.getBrain().getMemory(InitMemory.PICKABLE_ITEM.get()).isPresent();
    }
    /**
     * 这个函数用于开始任务
     * @param level: 当前世界
     * @param entity: 当前生物
     * @param time: 当前时间
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel level, AbstractPet entity, long time) {
        //获取可拾取物品列表
        ItemEntity target = entity.getBrain().getMemory(InitMemory.PICKABLE_ITEM.get()).orElse(null);
        //如果不为空
        if (target != null) {
            //设置目标位置
            BehaviorUtils.setWalkAndLookTargetMemories(entity, target, speedModifier, 0);
        }
        //如果为空，则清除LookTarget
        else{
            //清除LookTarget
            entity.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
        }        
    }

    @Override
    protected void tick(ServerLevel level, AbstractPet entity, long time) {
        ItemEntity target = entity.getBrain().getMemory(InitMemory.PICKABLE_ITEM.get()).orElse(null);
        if (target == null) {
            entity.getBrain().eraseMemory(InitMemory.PICKABLE_ITEM.get());
            return;
        }
        if (!target.isAlive() || target.hasPickUpDelay() || !target.getItem().is(InitTag.ENTITY_PICKABLE_ITEMS)) {
            entity.getBrain().eraseMemory(InitMemory.PICKABLE_ITEM.get());
            return;
        }
        if (entity.distanceToSqr(target) > PICKUP_DISTANCE_SQ) {
            return;
        }
        IItemHandler handler = entity.getCapability(Capabilities.ItemHandler.ENTITY, null);
        if (handler == null) {
            handler = entity.getBackpackHandler();
        }
        ItemStack stack = target.getItem();
        ItemStack remaining = insertIntoHandler(handler, stack);
        int pickedCount = stack.getCount() - remaining.getCount();
        if (pickedCount <= 0) {
            return;
        }
        entity.take(target, pickedCount);
        if (remaining.isEmpty()) {
            target.discard();
        } else {
            target.setItem(remaining);
        }
        entity.onItemPickup(target);
        entity.getBrain().eraseMemory(InitMemory.PICKABLE_ITEM.get());
    }

    private static ItemStack insertIntoHandler(IItemHandler handler, ItemStack stack) {
        ItemStack remaining = stack.copy();
        for (int slot = 0; slot < handler.getSlots() && !remaining.isEmpty(); slot++) {
            remaining = handler.insertItem(slot, remaining, false);
        }
        return remaining;
    }
}
