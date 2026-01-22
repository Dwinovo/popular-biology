package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.HachiwareModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.HachiwarePet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class HachiwareRender extends AbstractPetRender<HachiwarePet> {

    public HachiwareRender(Context renderManager) {
        super(renderManager, new HachiwareModel());
    }
}

