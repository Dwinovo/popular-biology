package com.dwinovo.chiikawa.client.render.impl;

import com.dwinovo.chiikawa.client.model.impl.UsagiModel;
import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.impl.UsagiPet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;

public class UsagiRender extends AbstractPetRender<UsagiPet> {

    public UsagiRender(Context renderManager) {
        super(renderManager, new UsagiModel());
    }
}

