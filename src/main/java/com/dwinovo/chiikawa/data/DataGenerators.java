package com.dwinovo.chiikawa.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.chiikawa.Chiikawa;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import java.util.Set;

@EventBusSubscriber(modid = Chiikawa.MODID)
public final class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        PackOutput output = event.getGenerator().getPackOutput();
        event.getGenerator().addProvider(true,
                new ModSoundDefinitionsProvider(output));
        event.getGenerator().addProvider(true,
                new ModItemModelProvider(output));
        event.getGenerator().addProvider(true,
                new ModLanguageProvider(output, "en_us"));
        event.getGenerator().addProvider(true,
                new ModLanguageProvider(output, "zh_cn"));
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        event.getGenerator().addProvider(true,
                new DatapackBuiltinEntriesProvider(output, lookupProvider, ModDatapackRegistries.BUILDER,
                        Set.of(Chiikawa.MODID)));
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookupProvider);
        event.getGenerator().addProvider(true, blockTagsProvider);
        event.getGenerator().addProvider(true,
                new ModItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter()));
        event.getGenerator().addProvider(true,
                new RecipeProvider.Runner(output, lookupProvider) {
                    @Override
                    public String getName() {
                        return "Chiikawa Recipes";
                    }

                    @Override
                    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
                        return new ModRecipeProvider(registries, output);
                    }
                });
        // Entity tags.
        event.getGenerator().addProvider(true, 
            new ModEntityTagsProvider(
                output,
                lookupProvider
            )
        );
    }
}
