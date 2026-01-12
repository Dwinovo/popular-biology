package com.dwinovo.popularbiology.entity.job.api;

import com.dwinovo.popularbiology.entity.AbstractPet;

import net.minecraft.world.entity.ai.Brain;

@FunctionalInterface
public interface IJobTickHandler {
    void tick(AbstractPet pet, Brain<AbstractPet> brain);
}
