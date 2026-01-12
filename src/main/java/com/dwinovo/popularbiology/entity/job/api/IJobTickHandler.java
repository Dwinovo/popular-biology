package com.dwinovo.popularbiology.entity.job.api;

import com.dwinovo.popularbiology.entity.AbstractPet;

@FunctionalInterface
public interface IJobTickHandler {
    void tick(AbstractPet pet);
}
