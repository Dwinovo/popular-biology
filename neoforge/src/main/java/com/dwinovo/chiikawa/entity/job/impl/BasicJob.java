package com.dwinovo.chiikawa.entity.job.impl;

import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.entity.job.api.IJobInitHandler;
import com.dwinovo.chiikawa.entity.job.api.IJobTickHandler;
import com.dwinovo.chiikawa.entity.job.api.IPetJob;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BasicJob implements IPetJob {
    private final int id;
    private final int priority;
    private final TagKey<Item> toolTag;
    private final IJobInitHandler initHandler;
    private final IJobTickHandler tickHandler;

    public BasicJob(int id, int priority, TagKey<Item> toolTag, IJobInitHandler initHandler, IJobTickHandler tickHandler) {
        this.id = id;
        this.priority = priority;
        this.toolTag = toolTag;
        this.initHandler = initHandler;
        this.tickHandler = tickHandler;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public boolean canAssume(AbstractPet pet) {
        ItemStack stack = pet.getBackpack().getItem(0);
        return stack.is(toolTag);
    }

    @Override
    public void initBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        initHandler.init(pet, brain);
    }

    @Override
    public void tickBrain(AbstractPet pet, Brain<AbstractPet> brain) {
        tickHandler.tick(pet,brain);
    }
}

