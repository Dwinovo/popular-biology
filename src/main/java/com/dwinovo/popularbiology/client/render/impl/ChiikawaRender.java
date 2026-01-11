package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.ChiikawaModel;
import com.dwinovo.popularbiology.entity.impl.ChiikawaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ChiikawaRender extends GeoEntityRenderer<ChiikawaPet> {

    public ChiikawaRender(Context renderManager) {
        super(renderManager, new ChiikawaModel());
    }
}
