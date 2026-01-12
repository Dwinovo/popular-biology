package com.dwinovo.popularbiology.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitTag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModEntityTagsProvider extends EntityTypeTagsProvider{
    public ModEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, PopularBiology.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(InitTag.ENTITY_HOSTILE_ENTITY)
            .add(EntityType.BLAZE)
            .add(EntityType.CAVE_SPIDER)
            .add(EntityType.DROWNED)
            .add(EntityType.EVOKER)
            .add(EntityType.GUARDIAN)
            .add(EntityType.HUSK)
            .add(EntityType.ILLUSIONER)
            .add(EntityType.MAGMA_CUBE)
            .add(EntityType.PHANTOM)
            .add(EntityType.PIGLIN)
            .add(EntityType.PIGLIN_BRUTE)
            .add(EntityType.PILLAGER)
            .add(EntityType.SILVERFISH)
            .add(EntityType.SKELETON)
            .add(EntityType.SLIME)
            .add(EntityType.SPIDER)
            .add(EntityType.STRAY)
            .add(EntityType.VEX)
            .add(EntityType.VINDICATOR)
            .add(EntityType.WITCH)
            .add(EntityType.WITHER_SKELETON)
            .add(EntityType.ZOGLIN)
            .add(EntityType.ZOMBIE);
    }
}
