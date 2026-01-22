package com.dwinovo.chiikawa;

import net.fabricmc.api.ModInitializer;

public class ChiikawaFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Chiikawa Fabric world!");
        CommonClass.init();
    }
}
