package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.RakkoModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.RakkoPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class RakkoRender extends AbstractPetRender<RakkoPet> {

    public RakkoRender(Context renderManager) {
        super(renderManager, new RakkoModel());
    }
}

