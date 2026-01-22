package com.dwinovo.chiikawa;

import com.dwinovo.chiikawa.client.render.impl.ChiikawaRender;
import com.dwinovo.chiikawa.client.render.impl.HachiwareRender;
import com.dwinovo.chiikawa.client.render.impl.KurimanjuRender;
import com.dwinovo.chiikawa.client.render.impl.MomongaRender;
import com.dwinovo.chiikawa.client.render.impl.RakkoRender;
import com.dwinovo.chiikawa.client.render.impl.ShisaRender;
import com.dwinovo.chiikawa.client.render.impl.UsagiRender;
import com.dwinovo.chiikawa.client.screen.PetBackpackScreen;
import com.dwinovo.chiikawa.init.InitEntity;
import com.dwinovo.chiikawa.init.InitMenu;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class ChiikawaFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(InitEntity.USAGI_PET.get(), UsagiRender::new);
        EntityRendererRegistry.register(InitEntity.HACHIWARE_PET.get(), HachiwareRender::new);
        EntityRendererRegistry.register(InitEntity.CHIIKAWA_PET.get(), ChiikawaRender::new);
        EntityRendererRegistry.register(InitEntity.SHISA_PET.get(), ShisaRender::new);
        EntityRendererRegistry.register(InitEntity.MOMONGA_PET.get(), MomongaRender::new);
        EntityRendererRegistry.register(InitEntity.KURIMANJU_PET.get(), KurimanjuRender::new);
        EntityRendererRegistry.register(InitEntity.RAKKO_PET.get(), RakkoRender::new);

        MenuScreens.register(InitMenu.PET_BACKPACK.get(), PetBackpackScreen::new);
    }
}
