package com.dwinovo.popularbiology.init;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.impl.ChiikawaPet;
import com.dwinovo.popularbiology.entity.impl.HachiwarePet;
import com.dwinovo.popularbiology.entity.impl.KurimanjuPet;
import com.dwinovo.popularbiology.entity.impl.MomongaPet;
import com.dwinovo.popularbiology.entity.impl.RakkoPet;
import com.dwinovo.popularbiology.entity.impl.ShisaPet;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

public final class InitEntity {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, PopularBiology.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<UsagiPet>> USAGI_PET = ENTITY_TYPES.register("usagi",
            () -> EntityType.Builder.of(UsagiPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "usagi").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<HachiwarePet>> HACHIWARE_PET = ENTITY_TYPES.register("hachiware",
            () -> EntityType.Builder.of(HachiwarePet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "hachiware").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ChiikawaPet>> CHIIKAWA_PET = ENTITY_TYPES.register("chiikawa",
            () -> EntityType.Builder.of(ChiikawaPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "chiikawa").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ShisaPet>> SHISA_PET = ENTITY_TYPES.register("shisa",
            () -> EntityType.Builder.of(ShisaPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "shisa").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<MomongaPet>> MOMONGA_PET = ENTITY_TYPES.register("momonga",
            () -> EntityType.Builder.of(MomongaPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "momonga").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<KurimanjuPet>> KURIMANJU_PET = ENTITY_TYPES.register("kurimanju",
            () -> EntityType.Builder.of(KurimanjuPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "kurimanju").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<RakkoPet>> RAKKO_PET = ENTITY_TYPES.register("rakko",
            () -> EntityType.Builder.of(RakkoPet::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "rakko").toString()));

   
    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(USAGI_PET.get(), UsagiPet.createAttributes().build());
        event.put(HACHIWARE_PET.get(), HachiwarePet.createAttributes().build());
        event.put(CHIIKAWA_PET.get(), ChiikawaPet.createAttributes().build());
        event.put(SHISA_PET.get(), ShisaPet.createAttributes().build());
        event.put(MOMONGA_PET.get(), MomongaPet.createAttributes().build());
        event.put(KURIMANJU_PET.get(), KurimanjuPet.createAttributes().build());
        event.put(RAKKO_PET.get(), RakkoPet.createAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        registerSpawnPlacement(event, USAGI_PET);
        registerSpawnPlacement(event, HACHIWARE_PET);
        registerSpawnPlacement(event, CHIIKAWA_PET);
        registerSpawnPlacement(event, SHISA_PET);
        registerSpawnPlacement(event, MOMONGA_PET);
        registerSpawnPlacement(event, KURIMANJU_PET);
        registerSpawnPlacement(event, RAKKO_PET);
    }

    private static <T extends Animal> void registerSpawnPlacement(
            RegisterSpawnPlacementsEvent event,
            DeferredHolder<EntityType<?>, EntityType<T>> entity) {
        event.register(entity.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
