package com.dwinovo.chiikawa.platform.services;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public interface IRegistryHelper {
    <T> Supplier<T> register(Registry<? super T> registry, ResourceLocation id, Supplier<? extends T> factory);

    <T> Registry<T> createRegistry(ResourceKey<Registry<T>> key, ResourceLocation defaultId, boolean sync);

    void registerToEventBus(Object eventBus);
}
