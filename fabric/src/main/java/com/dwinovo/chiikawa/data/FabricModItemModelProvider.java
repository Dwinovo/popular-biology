package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.init.InitItems;

import java.util.Optional;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;


public class FabricModItemModelProvider extends FabricModelProvider {


    public FabricModItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        generateSpawnEggs(itemModelGenerator);

        // Weapons have prebuilt models under resources; no datagen needed.
    }

    private static void generateSpawnEggs(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(InitItems.USAGI_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.HACHIWARE_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.CHIIKAWA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.SHISA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.MOMONGA_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.KURIMANJU_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(InitItems.RAKKO_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);

    }
}
