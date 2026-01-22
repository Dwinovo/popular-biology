package com.dwinovo.chiikawa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import com.dwinovo.chiikawa.init.InitEntity;
import com.dwinovo.chiikawa.init.InitMemory;
import com.dwinovo.chiikawa.init.InitMenu;
import com.dwinovo.chiikawa.init.InitRegistry;
import com.dwinovo.chiikawa.init.InitSensor;
import com.dwinovo.chiikawa.init.InitActivity;
import com.dwinovo.chiikawa.init.InitCapabilities;
import com.dwinovo.chiikawa.init.InitSounds;
import com.dwinovo.chiikawa.init.InitItems;
import com.dwinovo.chiikawa.init.InitTabs;
import com.dwinovo.chiikawa.platform.NeoForgePlatformRegistryAccess;
import com.dwinovo.chiikawa.platform.Services;

// Must match the mod id in META-INF/neoforge.mods.toml.
@Mod(Chiikawa.MODID)
public class Chiikawa {
    
    public static final String MODID = "chiikawa";

    public Chiikawa(IEventBus modEventBus) {

        NeoForgePlatformRegistryAccess.register(modEventBus);

        InitRegistry.init();
        InitMemory.init();
        InitSensor.init();
        InitActivity.init();
        InitSounds.init();
        InitMenu.init();
        InitEntity.init();
        InitItems.init();
        InitTabs.init();
        InitCapabilities.init();
        Services.REGISTRY.registerToEventBus(modEventBus);
        Services.ENTITY.registerToEventBus(modEventBus);

        InitCapabilities.register(modEventBus);

    }



}


