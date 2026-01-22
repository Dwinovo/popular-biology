package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.KurimanjuModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.KurimanjuPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class KurimanjuRender extends AbstractPetRender<KurimanjuPet> {

    public KurimanjuRender(Context renderManager) {
        super(renderManager, new KurimanjuModel());
    }
}

