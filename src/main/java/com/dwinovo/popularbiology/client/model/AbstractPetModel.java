package com.dwinovo.popularbiology.client.model;

import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.AbstractPet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public abstract class AbstractPetModel<T extends AbstractPet> extends GeoModel<T> {
    private final String id;

    protected AbstractPetModel(String id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "geo/" + id + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "textures/entities/" + id + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return ResourceLocation.fromNamespaceAndPath(PopularBiology.MODID, "animations/" + id + ".animation.json");
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        EntityModelData modelData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        GeoBone headBone = this.getAnimationProcessor().getBone("AllHead");
        if (headBone != null) {
            float netHeadYaw = modelData.netHeadYaw();
            float headPitch = modelData.headPitch();
            headBone.setRotY(netHeadYaw * ((float) Math.PI / 180F));
            headBone.setRotX(headPitch * ((float) Math.PI / 180F));
        }

        GeoBone leftEarBone = this.getAnimationProcessor().getBone("LeftEar");
        GeoBone rightEarBone = this.getAnimationProcessor().getBone("RightEar");
        GeoBone tailBone = this.getAnimationProcessor().getBone("tail");

        double ageInTicks = animationState.getData(DataTickets.TICK);
        float limbSwingAmount = animationState.getLimbSwingAmount();

        float breathingSpeed = 0.1F;
        float earSwingAmount = 0.1F;
        float earTwistAmount = 0.1F;
        float earBackwardSwing = -limbSwingAmount * 1.0F;

        if (leftEarBone != null) {
            leftEarBone.setRotY(Mth.cos((float) ageInTicks * breathingSpeed) * earSwingAmount - earBackwardSwing);
            leftEarBone.setRotZ(Mth.sin((float) ageInTicks * breathingSpeed) * earTwistAmount);
        }

        if (rightEarBone != null) {
            rightEarBone.setRotY(-Mth.cos((float) ageInTicks * breathingSpeed) * earSwingAmount + earBackwardSwing);
            rightEarBone.setRotZ(-Mth.sin((float) ageInTicks * breathingSpeed) * earTwistAmount);
        }

        if (tailBone != null) {
            tailBone.setRotY(Mth.cos((float) ageInTicks * breathingSpeed) * 0.15F);
        }
    }
}
