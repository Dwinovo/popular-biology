package com.dwinovo.popularbiology.entity.brain.sensor;

import com.dwinovo.popularbiology.entity.AbstractPet;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.server.level.ServerLevel;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitTag;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.utils.Utils;
import net.minecraft.world.phys.AABB;
import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.world.entity.item.ItemEntity;
// 这个类检测周围是否有可以拾取的物品
public class PetPickableItemSensor extends Sensor<AbstractPet>{
    // 垂直范围
    private static final int VERTICAL_SEARCH_RANGE = 7;
    /**
     * 这个函数用于初始化传感器
     */
    public PetPickableItemSensor() {
        //30tick检测一次
        super(30);
    }
    /**
     * 这个函数用于获取需要检测的记忆类型
     * @return: 需要检测的记忆类型
     */
    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(InitMemory.PICKABLE_ITEM.get());
    }
    /**
     * 这个函数用于检测周围是否有可以拾取的物品
     * @param level: 当前世界
     * @param entity: 当前生物
     */
    @SuppressWarnings("null")
    @Override
    protected void doTick(ServerLevel level, AbstractPet entity) {
        // 只有当宠物被驯化并且处于工作状态，并且Action为0，才进行检测
        if (entity.isTame() && entity.getPetMode() == PetMode.WORK) {
            // 以宠物为中心，周围VERTICAL_SEARCH_RANGE格区域
            AABB aabb = entity.getBoundingBox().inflate(VERTICAL_SEARCH_RANGE, VERTICAL_SEARCH_RANGE, VERTICAL_SEARCH_RANGE);
            // 获取最近的可拾取物品
            ItemEntity target = level.getEntitiesOfClass(ItemEntity.class, aabb, ItemEntity::isAlive).stream()
                    // 有拾取延迟的不处理
                    .filter(e -> !e.hasPickUpDelay())
                    // 只拾取指定物品
                    .filter(e -> e.getItem().is(InitTag.ENTITY_PICKABLE_ITEMS))
                    // 位置可以到达
                    .filter(e -> Utils.canReach(entity, e.blockPosition()))
                    // 距离最近的一个
                    .min(Comparator.comparingDouble(entity::distanceToSqr))
                    .orElse(null);
            // 更新记忆系统（需要确保记忆模块类型匹配）
            if (target != null) {
                entity.getBrain().setMemory(InitMemory.PICKABLE_ITEM.get(), target);
            } else {
                entity.getBrain().eraseMemory(InitMemory.PICKABLE_ITEM.get());
            }
        } else {
            entity.getBrain().eraseMemory(InitMemory.PICKABLE_ITEM.get());
        }
    }
}
