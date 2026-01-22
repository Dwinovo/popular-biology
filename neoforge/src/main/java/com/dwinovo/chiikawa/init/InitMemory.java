package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.Chiikawa;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class InitMemory {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_TYPES =
        DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, Chiikawa.MODID);

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> HARVEST_POS =
        MEMORY_TYPES.register("harvest_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> PLANT_POS =
        MEMORY_TYPES.register("plant_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<BlockPos>> CONTAINER_POS =
        MEMORY_TYPES.register("container_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));

    public static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<net.minecraft.world.entity.item.ItemEntity>> PICKABLE_ITEM =
        MEMORY_TYPES.register("pickable_item", () -> new MemoryModuleType<>(Optional.empty()));

    private InitMemory() {
    }

    public static void register(IEventBus modEventBus) {
        MEMORY_TYPES.register(modEventBus);
    }
}


