package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.ChiikawaModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.ChiikawaPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class ChiikawaRender extends AbstractPetRender<ChiikawaPet> {

    public ChiikawaRender(Context renderManager) {
        super(renderManager, new ChiikawaModel());
    }
}

