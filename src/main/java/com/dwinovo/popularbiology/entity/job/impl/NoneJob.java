package com.dwinovo.popularbiology.entity.job.impl;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.brain.handler.NoneJobHandler;
import com.dwinovo.popularbiology.entity.job.api.IPetJob;
import net.minecraft.world.entity.ai.Brain;

public class NoneJob implements IPetJob {
    private final int id;

    public NoneJob(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean canAssume(AbstractPet pet) {
        return true;
    }

    @Override
    public void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        NoneJobHandler.initBrain(pet, brain);
    }

    @Override
    public void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        NoneJobHandler.tickBrain(pet,brain);
    }
}
