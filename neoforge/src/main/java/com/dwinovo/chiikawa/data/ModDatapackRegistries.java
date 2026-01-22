package com.dwinovo.chiikawa.data;

import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModDatapackRegistries {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap);

    private ModDatapackRegistries() {
    }
}

