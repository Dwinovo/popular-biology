package com.dwinovo.popularbiology.data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitSounds;

import net.minecraft.data.PackOutput;
import net.minecraft.server.packs.PackType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public final class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    private static final int MAX_VARIANTS = 64;
    private final ExistingFileHelper existingFileHelper;

    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PopularBiology.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    public void registerSounds() {
        Map<String, List<ResourceLocation>> variants = collectVariants(InitSounds.entries());
        for (InitSounds.SoundEntry entry : InitSounds.entries()) {
            List<ResourceLocation> sounds = variants.get(entry.path());
            if (sounds == null || sounds.isEmpty()) {
                continue;
            }
            SoundDefinition.Sound[] soundEntries = sounds.stream()
                    .sorted(Comparator.comparing(ResourceLocation::toString))
                    .map(SoundDefinitionsProvider::sound)
                    .toArray(SoundDefinition.Sound[]::new);
            add(entry.holder(), SoundDefinition.definition().with(soundEntries));
        }
    }

    private Map<String, List<ResourceLocation>> collectVariants(List<InitSounds.SoundEntry> entries) {
        Map<String, List<ResourceLocation>> variants = new HashMap<>();
        for (InitSounds.SoundEntry entry : entries) {
            List<ResourceLocation> sounds = findVariants(entry.path());
            variants.put(entry.path(), sounds);
        }
        return variants;
    }

    private List<ResourceLocation> findVariants(String basePath) {
        List<ResourceLocation> sounds = new java.util.ArrayList<>();
        for (int i = 1; i <= MAX_VARIANTS; i++) {
            ResourceLocation candidate = ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, basePath + "_" + i);
            if (!existingFileHelper.exists(candidate, PackType.CLIENT_RESOURCES, ".ogg", "sounds")) {
                break;
            }
            sounds.add(candidate);
        }

        if (sounds.isEmpty()) {
            ResourceLocation direct = ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, basePath);
            if (existingFileHelper.exists(direct, PackType.CLIENT_RESOURCES, ".ogg", "sounds")) {
                sounds.add(direct);
            }
        }

        return sounds;
    }
}
