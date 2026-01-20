package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitItems;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public final class ModItemModelProvider extends ModelProvider {
    public ModItemModelProvider(PackOutput output) {
        super(output, Chiikawa.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        // Spawn eggs.
        itemModels.generateSpawnEgg(InitItems.USAGI_SPAWN_EGG.get(), 0xf28907, 0xF2CB07);
        itemModels.generateSpawnEgg(InitItems.HACHIWARE_SPAWN_EGG.get(), 0x00FFFF, 0x89cff0);
        itemModels.generateSpawnEgg(InitItems.CHIIKAWA_SPAWN_EGG.get(), 0xE7CCCC, 0xEDE8DC);
        itemModels.generateSpawnEgg(InitItems.SHISA_SPAWN_EGG.get(), 0xFFA500, 0xDFFF00);
        itemModels.generateSpawnEgg(InitItems.MOMONGA_SPAWN_EGG.get(), 0x0ABAB5, 0x00008B);
        itemModels.generateSpawnEgg(InitItems.KURIMANJU_SPAWN_EGG.get(), 0xdac24e, 0xda8b4e);
        itemModels.generateSpawnEgg(InitItems.RAKKO_SPAWN_EGG.get(), 0xeaffd0, 0xeaeaea);

        // Weapons use custom Blockbench models under src/main/resources.
        itemModels.declareCustomModelItem(InitItems.USAGI_WEAPON.get());
        itemModels.declareCustomModelItem(InitItems.HACHIWARE_WEAPON.get());
        itemModels.declareCustomModelItem(InitItems.CHIIKAWA_WEAPON.get());
    }
}


