package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.MomongaModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.MomongaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class MomongaRender extends AbstractPetRender<MomongaPet> {

    public MomongaRender(Context renderManager) {
        super(renderManager, new MomongaModel());
    }
}

