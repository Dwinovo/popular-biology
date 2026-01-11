package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.RakkoModel;
import com.dwinovo.popularbiology.entity.impl.RakkoPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RakkoRender extends GeoEntityRenderer<RakkoPet> {

    public RakkoRender(Context renderManager) {
        super(renderManager, new RakkoModel());
    }
}
