package com.dwinovo.chiikawa.data;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitSounds;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public final class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    private static final int MAX_VARIANTS = 64;
    private final Path resourceRoot;

    public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, Chiikawa.MODID);
        this.resourceRoot = resolveResourceRoot(output);
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
            ResourceLocation candidate = ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, basePath + "_" + i);
            if (!soundExists(candidate)) {
                break;
            }
            sounds.add(candidate);
        }

        if (sounds.isEmpty()) {
            ResourceLocation direct = ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, basePath);
            if (soundExists(direct)) {
                sounds.add(direct);
            }
        }

        return sounds;
    }

    private boolean soundExists(ResourceLocation sound) {
        Path path = resourceRoot.resolve("assets")
                .resolve(sound.getNamespace())
                .resolve("sounds")
                .resolve(sound.getPath() + ".ogg");
        return Files.exists(path);
    }

    private static Path resolveResourceRoot(PackOutput output) {
        Path outputRoot = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK);
        Path current = outputRoot.toAbsolutePath();
        for (int i = 0; i < 8 && current != null; i++) {
            Path candidate = current.resolve("src").resolve("main").resolve("resources");
            if (Files.exists(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }
        return Path.of(System.getProperty("user.dir"), "src", "main", "resources");
    }
}


