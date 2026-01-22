package com.dwinovo.chiikawa.entity.job.api;

import com.dwinovo.chiikawa.entity.AbstractPet;
import net.minecraft.world.entity.ai.Brain;

public interface IPetJob {
    int getId();

    int getPriority();

    boolean canAssume(AbstractPet pet);

    void initBrain(AbstractPet pet, Brain<AbstractPet> brain);

    void tickBrain(AbstractPet pet, Brain<AbstractPet> brain);
}

