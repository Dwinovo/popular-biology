package com.dwinovo.popularbiology.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitTag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.empty()),
                PopularBiology.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(InitTag.ENTITY_FARMER_TOOLS)
                .add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
        tag(InitTag.ENTITY_FENCER_TOOLS)
                .add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
        tag(InitTag.ENTITY_ARCHER_TOOLS)
                .add(Items.BOW);
        tag(InitTag.ENTITY_PLANT_CROPS)
            .add(Items.MELON_SEEDS)
            .add(Items.PUMPKIN_SEEDS)
            .add(Items.WHEAT_SEEDS)
            .add(Items.BEETROOT_SEEDS)
            .add(Items.POTATO)
            .add(Items.CARROT);
        tag(InitTag.ENTITY_DELIVER_ITEMS)
            .add(Items.WHEAT)
            .add(Items.BEETROOT)
            .add(Items.POTATO)
            .add(Items.CARROT)
            .add(Items.MELON_SLICE)
            .add(Items.PUMPKIN);
        tag(InitTag.ENTITY_PICKABLE_ITEMS)
            .add(Items.WHEAT, Items.WHEAT_SEEDS)
            .add(Items.POTATO)
            .add(Items.CARROT)
            .add(Items.BEETROOT)
            .add(Items.MELON_SLICE)
            .add(Items.PUMPKIN);
            
                
    }
}
