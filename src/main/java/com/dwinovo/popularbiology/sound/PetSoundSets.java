package com.dwinovo.popularbiology.sound;

import java.util.List;

import com.dwinovo.popularbiology.init.InitSounds;

public final class PetSoundSets {
    public static final PetSoundSet CHIIKAWA = new PetSoundSet(
            null,
            InitSounds.CHIIKAWA_INJURED,
            null,
            InitSounds.CHIIKAWA_TAME,
            List.of()
    );

    public static final PetSoundSet HACHIWARE = new PetSoundSet(
            null,
            InitSounds.HACHIWARE_INJURED,
            null,
            InitSounds.HACHIWARE_TAME,
            List.of()
    );

    public static final PetSoundSet KURIMANJU = new PetSoundSet(
            null,
            null,
            null,
            InitSounds.KURIMANJU_TAME,
            List.of()
    );

    public static final PetSoundSet MOMONGA = new PetSoundSet(
            null,
            InitSounds.MOMONGA_INJURED,
            null,
            InitSounds.MOMONGA_TAME,
            List.of()
    );

    public static final PetSoundSet RAKKO = new PetSoundSet(
            null,
            null,
            null,
            InitSounds.RAKKO_TAME,
            List.of()
    );

    public static final PetSoundSet SHISA = new PetSoundSet(
            null,
            null,
            null,
            InitSounds.SHISA_TAME,
            List.of()
    );

    public static final PetSoundSet USAGI = new PetSoundSet(
            InitSounds.USAGI_ATTACK,
            InitSounds.USAGI_INJURED,
            null,
            InitSounds.USAGI_TAME,
            List.of(
                    InitSounds.USAGI_AMBIENT
            )
    );

    private PetSoundSets() {
    }
}
