package com.dwinovo.chiikawa.entity.brain.task.tameable;

import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import java.util.Map;
import net.minecraft.server.level.ServerLevel;
import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.entity.PetMode;
import com.dwinovo.chiikawa.init.InitMemory;
import com.dwinovo.chiikawa.init.InitTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.minecraft.world.item.ItemStack;
// Picks up nearby items.
public class PickUpItemTask extends Behavior<AbstractPet> {
    // Move speed.
    private final float speedModifier;
    private static final double PICKUP_DISTANCE_SQ = 2.25D;
    // Required memories.
    private static final Map<MemoryModuleType<?>, MemoryStatus> REQUIRED_MEMORIES = ImmutableMap.of(
        InitMemory.PICKABLE_ITEM.get(), MemoryStatus.VALUE_PRESENT
    );
    /**
     * Creates the task.
     * @param speedModifier move speed
     */
    public PickUpItemTask(float speedModifier) {
        super(REQUIRED_MEMORIES, 10);
        this.speedModifier = speedModifier;
    }
    /**
     * Checks whether the task can start.
     * @param level the server level
     * @param entity the pet entity
     * @return whether the task can start
     */
    @SuppressWarnings("null")
    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, AbstractPet entity) {
        return entity.isTame() && entity.getPetMode() == PetMode.WORK;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, AbstractPet entity, long time) {
        return entity.isTame()
            && entity.getPetMode() == PetMode.WORK
            && entity.getBrain().getMemory(InitMemory.PICKABLE_ITEM.get()).isPresent();
    }
    /**
     * Start behavior.
     * @param level the server level
     * @param entity the pet entity
     * @param time the current time
     */
    @SuppressWarnings("null")
    @Override
    protected void start(ServerLevel level, AbstractPet entity, long time) {
        ItemEntity target = entity.getBrain().getMemory(InitMemory.PICKABLE_ITEM.get()).orElse(null);
        if (target != null) {
            BehaviorUtils.setWalkAndLookTargetMemories(entity, target, speedModifier, 0);
        }
        else{
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
        ResourceHandler<ItemResource> handler = entity.getCapability(Capabilities.Item.ENTITY, null);
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

    private static ItemStack insertIntoHandler(ResourceHandler<ItemResource> handler, ItemStack stack) {
        ItemStack remaining = stack.copy();
        for (int slot = 0; slot < handler.size() && !remaining.isEmpty(); slot++) {
            remaining = ItemUtil.insertItemReturnRemaining(handler, slot, remaining, false, null);
        }
        return remaining;
    }
}

