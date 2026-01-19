package com.dwinovo.popularbiology.data;

import java.util.concurrent.CompletableFuture;

import com.dwinovo.popularbiology.PopularBiology;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.fml.common.EventBusSubscriber;
import java.util.Set;

@EventBusSubscriber(modid = PopularBiology.MODID)
public final class DataGenerators {
    private DataGenerators() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        event.getGenerator().addProvider(event.includeServer(),
                new DatapackBuiltinEntriesProvider(output, lookupProvider, ModDatapackRegistries.BUILDER,
                        Set.of(PopularBiology.MODID)));
        event.getGenerator().addProvider(event.includeClient(),
                new ModSoundDefinitionsProvider(output, existingFileHelper));
        event.getGenerator().addProvider(event.includeClient(),
                new ModItemModelProvider(output, existingFileHelper));
        event.getGenerator().addProvider(event.includeClient(),
                new ModLanguageProvider(output, "en_us"));
        event.getGenerator().addProvider(event.includeClient(),
                new ModLanguageProvider(output, "zh_cn"));
        event.getGenerator().addProvider(event.includeServer(),
                new ModItemTagsProvider(output, lookupProvider, existingFileHelper));
        // 注册实体标签生成器
        event.getGenerator().addProvider(event.includeServer(), 
            new ModEntityTagsProvider(
                output,
                lookupProvider,
                existingFileHelper
            )
        );
        event.getGenerator().addProvider(event.includeServer(), 
            new ModBlockTagsProvider(
                output,
                lookupProvider,
                existingFileHelper
            )
        );
    }
}
