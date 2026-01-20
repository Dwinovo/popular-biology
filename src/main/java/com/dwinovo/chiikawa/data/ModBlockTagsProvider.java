package com.dwinovo.chiikawa.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitTag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class ModBlockTagsProvider extends BlockTagsProvider{
    
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(output, lookupProvider, Chiikawa.MODID);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(InitTag.ENTITY_HARVEST_CROPS)
            .add(Blocks.WHEAT, Blocks.POTATOES, Blocks.CARROTS, Blocks.BEETROOTS, Blocks.PUMPKIN, Blocks.MELON);
        tag(InitTag.ENTITY_DELEVER_CONTAINER)
            .add(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.BARREL, Blocks.HOPPER);
    }
}

