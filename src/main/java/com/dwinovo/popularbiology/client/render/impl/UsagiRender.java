package com.dwinovo.popularbiology.client.render.impl;

import com.dwinovo.popularbiology.client.model.impl.UsagiModel;
import com.dwinovo.popularbiology.entity.impl.UsagiPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class UsagiRender extends GeoEntityRenderer<UsagiPet> {

    public UsagiRender(Context renderManager) {
        super(renderManager, new UsagiModel());
    }
}
