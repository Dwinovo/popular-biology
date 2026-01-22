package com.dwinovo.chiikawa;

import net.fabricmc.api.ModInitializer;
import com.dwinovo.chiikawa.init.InitMemory;
import com.dwinovo.chiikawa.init.InitRegistry;
import com.dwinovo.chiikawa.init.InitSensor;
import com.dwinovo.chiikawa.init.InitTag;
import com.dwinovo.chiikawa.init.InitActivity;
import com.dwinovo.chiikawa.init.InitSounds;
import com.dwinovo.chiikawa.init.InitMenu;
import com.dwinovo.chiikawa.init.InitEntity;
import com.dwinovo.chiikawa.init.InitItems;
import com.dwinovo.chiikawa.init.InitTabs;
import com.dwinovo.chiikawa.data.FabricBiomeModifications;
import com.dwinovo.chiikawa.platform.Services;

public class ChiikawaFabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        InitRegistry.init();
        InitMemory.init();
        InitSensor.init();
        InitTag.init();
        InitActivity.init();
        InitSounds.init();
        InitMenu.init();
        InitEntity.init();
        InitItems.init();
        InitTabs.init();
        FabricBiomeModifications.init();
        Services.ENTITY.registerAttributes();
        Services.ENTITY.registerSpawnPlacements();
        Constants.LOG.info("Hello Chiikawa Fabric world!");
        CommonClass.init();
    }
}
