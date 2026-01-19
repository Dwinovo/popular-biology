package com.dwinovo.popularbiology.data;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PopularBiology.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(InitItems.USAGI_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.HACHIWARE_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.CHIIKAWA_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.SHISA_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.MOMONGA_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.KURIMANJU_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
        withExistingParent(InitItems.RAKKO_SPAWN_EGG.getId().toString(), mcLoc("item/template_spawn_egg"));
    }
}
