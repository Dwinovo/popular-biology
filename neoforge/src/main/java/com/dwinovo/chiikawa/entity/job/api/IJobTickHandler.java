package com.dwinovo.chiikawa.entity.job.api;

import com.dwinovo.chiikawa.entity.AbstractPet;

import net.minecraft.world.entity.ai.Brain;

@FunctionalInterface
public interface IJobTickHandler {
    void tick(AbstractPet pet, Brain<AbstractPet> brain);
}

