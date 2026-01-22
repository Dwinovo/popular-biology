package com.dwinovo.chiikawa.compat.jade;

import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.init.InitRegistry;
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
        tooltip.add(Component.translatable("tooltip.chiikawa.pet_job",
            Component.translatable(jobKey)));
    }

    @Override
    public ResourceLocation getUid() {
        return ChiikawaJadePlugin.PET_JOB_UID;
    }

    private static String getJobKey(int jobId) {
        if (jobId == InitRegistry.FARMER_ID) {
            return "tooltip.chiikawa.pet_job.farmer";
        }
        if (jobId == InitRegistry.FENCER_ID) {
            return "tooltip.chiikawa.pet_job.fencer";
        }
        if (jobId == InitRegistry.ARCHER_ID) {
            return "tooltip.chiikawa.pet_job.archer";
        }
        if (jobId == InitRegistry.NONE_ID) {
            return "tooltip.chiikawa.pet_job.none";
        }
        return "tooltip.chiikawa.pet_job.unknown";
    }
}


