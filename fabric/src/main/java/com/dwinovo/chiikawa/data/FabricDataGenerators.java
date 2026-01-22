package com.dwinovo.chiikawa.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.core.HolderLookup;

public class FabricDataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider((output, registries) -> new FabricModLanguageProvider(output, "en_us", registries));
        pack.addProvider((output, registries) -> new FabricModLanguageProvider(output, "zh_cn", registries));
        pack.addProvider(FabricModItemModelProvider::new);
        pack.addProvider(FabricModBlockTagsProvider::new);
        pack.addProvider(FabricModItemTagsProvider::new);
        pack.addProvider(FabricModEntityTagsProvider::new);
        pack.addProvider(FabricModSoundsProvider::new);
        pack.addProvider((output, registries) -> new RecipeProvider.Runner(output, registries) {
            @Override
            public String getName() {
                return "Chiikawa Recipes";
            }

            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
                return new ModRecipeProvider(registries, output);
            }
        });
    }
}
