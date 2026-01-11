package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.HachiwareModel;
import com.dwinovo.popularbiology.entity.impl.HachiwarePet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HachiwareRender extends GeoEntityRenderer<HachiwarePet> {

    public HachiwareRender(Context renderManager) {
        super(renderManager, new HachiwareModel());
    }
}
