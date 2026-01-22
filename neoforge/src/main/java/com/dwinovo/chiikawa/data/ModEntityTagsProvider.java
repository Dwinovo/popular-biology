package com.dwinovo.chiikawa.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitTag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;

public class ModEntityTagsProvider extends EntityTypeTagsProvider{
    public ModEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, Chiikawa.MODID);
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


