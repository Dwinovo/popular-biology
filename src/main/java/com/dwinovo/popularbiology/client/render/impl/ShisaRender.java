package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.ShisaModel;
import com.dwinovo.popularbiology.entity.impl.ShisaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShisaRender extends GeoEntityRenderer<ShisaPet> {

    public ShisaRender(Context renderManager) {
        super(renderManager, new ShisaModel());
    }
}
