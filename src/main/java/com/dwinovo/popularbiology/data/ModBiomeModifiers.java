package com.dwinovo.popularbiology.data;

import java.util.List;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_PET_SPAWNS = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "add_pet_spawns"));

    private ModBiomeModifiers() {
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<EntityType<?>> entities = context.lookup(Registries.ENTITY_TYPE);

        context.register(ADD_PET_SPAWNS, new AddSpawnsBiomeModifier(
                HolderSet.direct(
                        biomes.getOrThrow(biomeKey("plains")),
                        biomes.getOrThrow(biomeKey("sunflower_plains")),
                        biomes.getOrThrow(biomeKey("savanna")),
                        biomes.getOrThrow(biomeKey("savanna_plateau")),
                        biomes.getOrThrow(biomeKey("desert")),
                        biomes.getOrThrow(biomeKey("swamp")),
                        biomes.getOrThrow(biomeKey("snowy_plains"))
                ),
                List.of(
                        new SpawnerData(entities.getOrThrow(entityKey("usagi")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("hachiware")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("chiikawa")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("shisa")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("momonga")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("kurimanju")).value(), 20, 1, 1),
                        new SpawnerData(entities.getOrThrow(entityKey("rakko")).value(), 20, 1, 1)
                )));
    }

    private static ResourceKey<Biome> biomeKey(String path) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("minecraft", path));
    }

    private static ResourceKey<EntityType<?>> entityKey(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, path));
    }
}
