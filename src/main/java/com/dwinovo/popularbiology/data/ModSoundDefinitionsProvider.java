package com.dwinovo.popularbiology.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.init.InitSounds;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public final class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    private static final Pattern VARIANT_SUFFIX = Pattern.compile("^(.*)_\\d+$");
    private final Path soundsDir;

    public ModSoundDefinitionsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PopularBiology.MODID, existingFileHelper);
        this.soundsDir = resolveSoundsDir(output);
    }

    @Override
    public void registerSounds() {
        Map<String, List<ResourceLocation>> variants = collectVariants();
        for (InitSounds.SoundEntry entry : InitSounds.entries()) {
            List<ResourceLocation> sounds = variants.get(entry.path());
            if (sounds == null || sounds.isEmpty()) {
                PopularBiology.LOGGER.warn("No sound variants found for event: {}", entry.path());
                continue;
            }
            SoundDefinition.Sound[] soundEntries = sounds.stream()
                    .sorted(Comparator.comparing(ResourceLocation::toString))
                    .map(SoundDefinitionsProvider::sound)
                    .toArray(SoundDefinition.Sound[]::new);
            add(entry.holder(), SoundDefinition.definition().with(soundEntries));
        }
    }

    private Map<String, List<ResourceLocation>> collectVariants() {
        Map<String, List<ResourceLocation>> variants = new HashMap<>();
        if (soundsDir == null || !Files.exists(soundsDir)) {
            PopularBiology.LOGGER.warn("Sounds directory not found: {}", soundsDir);
            return variants;
        }

        try (Stream<Path> stream = Files.walk(soundsDir)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".ogg"))
                    .map(path -> soundsDir.relativize(path).toString().replace('\\', '/'))
                    .forEach(relativePath -> {
                        String soundPath = relativePath.substring(0, relativePath.length() - ".ogg".length());
                        Matcher matcher = VARIANT_SUFFIX.matcher(soundPath);
                        if (!matcher.matches()) {
                            return;
                        }
                        String basePath = matcher.group(1);
                        variants.computeIfAbsent(basePath, ignored -> new ArrayList<>())
                                .add(ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, soundPath));
                    });
        } catch (IOException e) {
            PopularBiology.LOGGER.error("Failed to scan sounds directory for data generation.", e);
        }

        return variants;
    }

    private static Path resolveSoundsDir(PackOutput output) {
        Path outputRoot = output.getOutputFolder();
        Path projectRoot = outputRoot;
        for (int i = 0; i < 3 && projectRoot != null; i++) {
            projectRoot = projectRoot.getParent();
        }
        if (projectRoot == null) {
            return null;
        }
        return projectRoot.resolve("src/main/resources/assets/" + PopularBiology.MODID + "/sounds");
    }
}
