package com.dwinovo.chiikawa.entity.job.api;

import com.dwinovo.chiikawa.entity.AbstractPet;
import net.minecraft.world.entity.ai.Brain;

@FunctionalInterface
public interface IJobInitHandler {
    void init(AbstractPet pet, Brain<AbstractPet> brain);
}

