package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.data.BiomeSpawnData;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FabricBiomeModifications {
    private FabricBiomeModifications() {
    }

    public static void init() {
        var selector = BiomeSelectors.includeByKey(BiomeSpawnData.BIOME_KEYS);
        for (BiomeSpawnData.SpawnEntry entry : BiomeSpawnData.SPAWNS) {
            BuiltInRegistries.ENTITY_TYPE.getOptional(entry.entityKey()).ifPresent(entity -> {
                BiomeModifications.addSpawn(
                    selector,
                    entry.category(),
                    entity,
                    entry.weight(),
                    entry.minCount(),
                    entry.maxCount()
                );
            });
        }
    }
}
