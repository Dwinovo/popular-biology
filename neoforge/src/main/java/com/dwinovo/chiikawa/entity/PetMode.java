package com.dwinovo.chiikawa.entity;

import java.util.function.Function;

import net.minecraft.network.chat.Component;

public enum PetMode {
    FOLLOW(pet -> Component.translatable("message.chiikawa.pet_follow", pet.getDisplayName().getString())),
    SIT(pet -> Component.translatable("message.chiikawa.pet_sit", pet.getDisplayName().getString())),
    WORK(pet -> Component.translatable("message.chiikawa.pet_work", pet.getDisplayName().getString()));

    private final Function<AbstractPet, Component> message;

    PetMode(Function<AbstractPet, Component> message) {
        this.message = message;
    }

    public Component getMessage(AbstractPet pet) {
        return message.apply(pet);
    }

    public static PetMode fromId(int id) {
        PetMode[] values = values();
        if (id < 0 || id >= values.length) {
            return FOLLOW;
        }
        return values[id];
    }

    public PetMode next() {
        PetMode[] values = values();
        return values[(this.ordinal() + 1) % values.length];
    }
}

