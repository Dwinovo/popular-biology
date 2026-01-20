package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.Chiikawa;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderSet;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers.AddSpawnsBiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_PET_SPAWNS = ResourceKey.create(
            NeoForgeRegistries.Keys.BIOME_MODIFIERS,
            ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "add_pet_spawns"));

    private ModBiomeModifiers() {
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<EntityType<?>> entities = context.lookup(Registries.ENTITY_TYPE);
        WeightedList.Builder<SpawnerData> spawners = WeightedList.builder();

        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("usagi")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("hachiware")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("chiikawa")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("shisa")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("momonga")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("kurimanju")).value(), 1, 1), 20);
        spawners.add(new SpawnerData(entities.getOrThrow(entityKey("rakko")).value(), 1, 1), 20);

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
                spawners.build()));
    }

    private static ResourceKey<Biome> biomeKey(String path) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("minecraft", path));
    }

    private static ResourceKey<EntityType<?>> entityKey(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, path));
    }
}


