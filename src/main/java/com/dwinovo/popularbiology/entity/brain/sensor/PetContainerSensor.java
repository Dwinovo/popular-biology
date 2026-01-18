package com.dwinovo.popularbiology.entity.brain.sensor;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitTag;
import com.dwinovo.popularbiology.utils.BlockSearch;
import com.dwinovo.popularbiology.utils.Utils;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
// 这个类用于检测周围是否有可以传递的容器
public class PetContainerSensor extends Sensor<AbstractPet> {
    // 最大半径
    private static final int MAX_RADIUS = 5;
    // 垂直范围
    private static final int VERTICAL_RANGE = 1;
    /**
     * 这个函数用于获取需要检测的记忆类型
     * @return: 需要检测的记忆类型
     */
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            InitMemory.CONTAINER_POS.get()
        );
     }

    
    /**
     * 具体的检测逻辑
     * @param level: 当前世界
     * @param entity: 当前生物
     */
    @Override
     protected void doTick(ServerLevel level, AbstractPet entity) {
         //只有当处于工作状态，并且职业为农民，才进行检测
         boolean hasDeliverItem = hasTaggedItem(entity.getBackpack());
         if (entity.getPetMode() == PetMode.WORK && entity.getPetJobId() == InitRegistry.FARMER_ID && hasDeliverItem) {
            // 螺旋式检测周围是否有可以传递的容器，要求符合InitTag.ENTITY_DELEVER_CONTAINER标签，并且背包里面有可以传递的物品
            BlockSearch.spiralBlockSearch(level, entity, MAX_RADIUS, VERTICAL_RANGE,
            (lvl, pos, pet) -> 
                //pos位置必须可以到达
                Utils.canReach(pet, pos)
                //pos位置的方块必须是InitTag里面的
                && lvl.getBlockState(pos).is(InitTag.ENTITY_DELEVER_CONTAINER)
                //背包里面有可以传递的物品
                && hasDeliverItem
                // 检查容器是否有空余空间
                && canInsertContainer(lvl, pos, pet)
                //如果符合条件，则返回pos位置
            ).ifPresentOrElse(foundPos -> {
                //设置记忆
                entity.getBrain().setMemory(InitMemory.CONTAINER_POS.get(), foundPos);
            }, () -> {
                //否则清除记忆
                entity.getBrain().eraseMemory(InitMemory.CONTAINER_POS.get());
            });
        } else if (entity.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).isPresent()) {
            // 背包没物品时清除记忆，避免卡在传递活动
            entity.getBrain().eraseMemory(InitMemory.CONTAINER_POS.get());
        }
     }

    private static boolean canInsertContainer(ServerLevel level, net.minecraft.core.BlockPos pos, AbstractPet pet) {
        net.minecraft.world.level.block.entity.BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof net.minecraft.world.Container container)) {
            return false;
        }
        net.minecraft.world.Container backpack = pet.getBackpack();
        for (int i = 0; i < backpack.getContainerSize(); i++) {
            net.minecraft.world.item.ItemStack stack = backpack.getItem(i);
            if (stack.isEmpty() || !stack.is(InitTag.ENTITY_DELIVER_ITEMS)) {
                continue;
            }
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                net.minecraft.world.item.ItemStack slotStack = container.getItem(slot);
                if (slotStack.isEmpty()) {
                    return true;
                }
                if (net.minecraft.world.item.ItemStack.isSameItemSameComponents(slotStack, stack)
                    && slotStack.getCount() < slotStack.getMaxStackSize()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasTaggedItem(net.minecraft.world.Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            net.minecraft.world.item.ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.is(InitTag.ENTITY_DELIVER_ITEMS)) {
                return true;
            }
        }
        return false;
    }
}
