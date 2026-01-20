package com.dwinovo.chiikawa.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitTag;
import com.dwinovo.chiikawa.init.InitItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.minecraft.world.item.Items;


public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Chiikawa.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(InitTag.ENTITY_FARMER_TOOLS)
                .add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
        tag(InitTag.ENTITY_FENCER_TOOLS)
                .add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD)
                .add(InitItems.USAGI_WEAPON.get())
                .add(InitItems.HACHIWARE_WEAPON.get())
                .add(InitItems.CHIIKAWA_WEAPON.get());
        tag(InitTag.ENTITY_ARCHER_TOOLS)
                .add(Items.BOW);
        tag(InitTag.ENTITY_TAME_FOODS)
                .add(Items.APPLE)
                .add(Items.BAKED_POTATO)
                .add(Items.BREAD)
                .add(Items.CARROT)
                .add(Items.COOKED_BEEF)
                .add(Items.COOKED_CHICKEN)
                .add(Items.COOKED_COD)
                .add(Items.COOKED_MUTTON)
                .add(Items.COOKED_PORKCHOP)
                .add(Items.COOKED_RABBIT)
                .add(Items.COOKED_SALMON)
                .add(Items.COOKIE)
                .add(Items.GLOW_BERRIES)
                .add(Items.GOLDEN_APPLE)
                .add(Items.GOLDEN_CARROT)
                .add(Items.HONEY_BOTTLE)
                .add(Items.MELON_SLICE)
                .add(Items.MUSHROOM_STEW)
                .add(Items.PUMPKIN_PIE)
                .add(Items.POTATO)
                .add(Items.BEETROOT)
                .add(Items.RABBIT_STEW)
                .add(Items.SWEET_BERRIES);
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


