package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.MomongaModel;
import com.dwinovo.popularbiology.entity.impl.MomongaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MomongaRender extends GeoEntityRenderer<MomongaPet> {

    public MomongaRender(Context renderManager) {
        super(renderManager, new MomongaModel());
    }
}
