package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.KurimanjuModel;
import com.dwinovo.popularbiology.entity.impl.KurimanjuPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KurimanjuRender extends GeoEntityRenderer<KurimanjuPet> {

    public KurimanjuRender(Context renderManager) {
        super(renderManager, new KurimanjuModel());
    }
}
