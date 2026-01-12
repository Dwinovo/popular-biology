package com.dwinovo.popularbiology.entity.brain.task.tameable;

import com.google.common.collect.ImmutableMap;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

// 这个任务用于让生物坐下
public class SitBehavior<E extends AbstractPet> extends Behavior<E> {
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of();

    /**
     * 这个函数用于初始化任务
     */
    public SitBehavior() {
        // 初始化任务，超时时间100tick
        super(REQUIRED_MEMORIES, 100);
    }

    /**
     * 这个函数用于检查是否可以开始任务
     * @param level: 当前世界
     * @param entity: 当前生物
     * @return: 是否可以开始任务
     */
    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        // 如果生物处于坐下状态，则可以开始任务
        return entity.getPetMode() == PetMode.SIT;
    }
    @Override
    protected void tick(ServerLevel level, E entity, long gameTime) {
        // 清除目标位置
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        // 清除无法到达目标位置的时间
        entity.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
    }

    /**
     * 这个函数用于检查是否可以继续执行任务
     * @param level: 当前世界
     * @param entity: 当前生物
     * @param gameTime: 当前时间
     * @return: 是否可以继续执行任务
     */
    @Override
    protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
        // 持续条件与启动条件一致
        return entity.getPetMode() == PetMode.SIT;
    }

    
}
