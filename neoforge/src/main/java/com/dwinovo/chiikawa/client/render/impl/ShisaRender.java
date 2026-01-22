package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.ShisaModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.ShisaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class ShisaRender extends AbstractPetRender<ShisaPet> {

    public ShisaRender(Context renderManager) {
        super(renderManager, new ShisaModel());
    }
}

