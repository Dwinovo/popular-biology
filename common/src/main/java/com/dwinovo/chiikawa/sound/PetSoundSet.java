package com.dwinovo.chiikawa.sound;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;

public final class PetSoundSet {
    public static final PetSoundSet EMPTY = new PetSoundSet(null, null, null, null, List.of());

    private final Supplier<SoundEvent> attackSound;
    private final Supplier<SoundEvent> hurtSound;
    private final Supplier<SoundEvent> deathSound;
    private final Supplier<SoundEvent> tameSound;
    private final List<Supplier<SoundEvent>> ambientSounds;

    public PetSoundSet(Supplier<SoundEvent> attackSound, Supplier<SoundEvent> hurtSound,
            Supplier<SoundEvent> deathSound, Supplier<SoundEvent> tameSound,
            List<Supplier<SoundEvent>> ambientSounds) {
        this.attackSound = attackSound;
        this.hurtSound = hurtSound;
        this.deathSound = deathSound;
        this.tameSound = tameSound;
        this.ambientSounds = ambientSounds == null ? List.of() : List.copyOf(ambientSounds);
    }

    public SoundEvent getAttackSound() {
        return resolve(attackSound);
    }

    public SoundEvent getHurtSound() {
        return resolve(hurtSound);
    }

    public SoundEvent getDeathSound() {
        return resolve(deathSound);
    }

    public SoundEvent getTameSound() {
        return resolve(tameSound);
    }

    public SoundEvent pickAmbientSound(RandomSource random) {
        if (ambientSounds.isEmpty()) {
            return null;
        }
        return resolve(ambientSounds.get(random.nextInt(ambientSounds.size())));
    }

    private static SoundEvent resolve(Supplier<SoundEvent> sound) {
        return sound == null ? null : sound.get();
    }
}

