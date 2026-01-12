package com.dwinovo.popularbiology.compat.jade;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.init.InitRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum PetJobComponentProvider implements IEntityComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof AbstractPet pet)) {
            return;
        }
        String jobKey = getJobKey(pet.getPetJobId());
        tooltip.add(Component.translatable("tooltip.popularbiology.pet_job",
            Component.translatable(jobKey)));
    }

    @Override
    public ResourceLocation getUid() {
        return PopularBiologyJadePlugin.PET_JOB_UID;
    }

    private static String getJobKey(int jobId) {
        if (jobId == InitRegistry.FARMER_ID) {
            return "tooltip.popularbiology.pet_job.farmer";
        }
        if (jobId == InitRegistry.FENCER_ID) {
            return "tooltip.popularbiology.pet_job.fencer";
        }
        if (jobId == InitRegistry.ARCHER_ID) {
            return "tooltip.popularbiology.pet_job.archer";
        }
        if (jobId == InitRegistry.NONE_ID) {
            return "tooltip.popularbiology.pet_job.none";
        }
        return "tooltip.popularbiology.pet_job.unknown";
    }
}
