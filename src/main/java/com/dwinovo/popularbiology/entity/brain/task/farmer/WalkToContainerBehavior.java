package com.dwinovo.popularbiology.entity.brain.task.farmer;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitTag;

import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
// 这个任务用于走到容器旁边
public class WalkToContainerBehavior extends Behavior<AbstractPet>{
    // 需要检测的记忆类型
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.CONTAINER_POS.get(), MemoryStatus.VALUE_PRESENT
    );
    // 速度
    private final float speed;
    /**
     * 这个函数用于初始化任务
     * @param speed: 速度
     */
    public WalkToContainerBehavior(float speed) {
        // 初始化任务，超时时间15
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
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractPet pet) {
        // HarvestPos和PlantPos不存在，处于工作模式，并且背包里面符合Tag
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID) {
            return false;
        }
        if (pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()
            || pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            return false;
        }
        if (!pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).isPresent()) {
            return false;
        }
        net.minecraft.core.BlockPos target = pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).get();
        if (!world.getBlockState(target).is(InitTag.ENTITY_DELEVER_CONTAINER)) {
            pet.getBrain().eraseMemory(InitMemory.CONTAINER_POS.get());
            return false;
        }
        if (!hasTaggedItem(pet.getBackpack(), InitTag.ENTITY_DELIVER_ITEMS)) {
            return false;
        }
        if (!canInsertContainer(world, target, pet)) {
            pet.getBrain().eraseMemory(InitMemory.CONTAINER_POS.get());
            return false;
        }
        return true;
    }
    /**
     * 这个函数用于开始任务
     * @param world: 当前世界
     * @param pet: 当前生物
     * @param time: 当前时间
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel world, AbstractPet pet, long time) {
        BehaviorUtils.setWalkAndLookTargetMemories(pet, pet.getBrain().getMemory(InitMemory.CONTAINER_POS.get()).get(), speed, 3);
    }
    

    private static boolean hasTaggedItem(net.minecraft.world.Container container, net.minecraft.tags.TagKey<net.minecraft.world.item.Item> tag) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            net.minecraft.world.item.ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && stack.is(tag)) {
                return true;
            }
        }
        return false;
    }

    private static boolean canInsertContainer(ServerLevel level, net.minecraft.core.BlockPos pos, AbstractPet pet) {
        IItemHandler handler = findItemHandler(level, pos);
        if (handler != null) {
            net.minecraft.world.Container backpack = pet.getBackpack();
            for (int i = 0; i < backpack.getContainerSize(); i++) {
                net.minecraft.world.item.ItemStack stack = backpack.getItem(i);
                if (stack.isEmpty() || !stack.is(InitTag.ENTITY_DELIVER_ITEMS)) {
                    continue;
                }
                net.minecraft.world.item.ItemStack testStack = stack.copy();
                testStack.setCount(1);
                for (int slot = 0; slot < handler.getSlots(); slot++) {
                    net.minecraft.world.item.ItemStack remaining = handler.insertItem(slot, testStack, true);
                    if (remaining.isEmpty()) {
                        return true;
                    }
                }
            }
            return false;
        }
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

    private static IItemHandler findItemHandler(ServerLevel level, net.minecraft.core.BlockPos pos) {
        IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, null);
        if (handler != null) {
            return handler;
        }
        for (Direction direction : Direction.values()) {
            handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, direction);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }
}
