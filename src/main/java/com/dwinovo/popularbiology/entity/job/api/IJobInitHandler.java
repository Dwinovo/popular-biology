package com.dwinovo.popularbiology.entity.job.api;

import com.dwinovo.popularbiology.entity.AbstractPet;
import net.minecraft.world.entity.ai.Brain;

@FunctionalInterface
public interface IJobInitHandler {
    void init(AbstractPet pet, Brain<AbstractPet> brain);
}
