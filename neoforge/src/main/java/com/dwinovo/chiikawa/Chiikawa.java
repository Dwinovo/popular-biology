package com.dwinovo.chiikawa;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
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

// Must match the mod id in META-INF/neoforge.mods.toml.
@Mod(Chiikawa.MODID)
public class Chiikawa {
    
    public static final String MODID = "chiikawa";

    public Chiikawa(IEventBus modEventBus, ModContainer modContainer) {

        InitEntity.register(modEventBus);
        InitMemory.register(modEventBus);
        InitMenu.register(modEventBus);
        InitRegistry.register(modEventBus);
        InitSensor.register(modEventBus);
        InitActivity.register(modEventBus);
        InitCapabilities.register(modEventBus);
        InitSounds.register(modEventBus);
        InitItems.register(modEventBus);
        InitTabs.register(modEventBus);

        modEventBus.addListener(InitEntity::registerAttributes);
        modEventBus.addListener(InitEntity::registerSpawnPlacements);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



}


