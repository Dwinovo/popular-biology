package com.dwinovo.popularbiology;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import com.dwinovo.popularbiology.init.InitEntity;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitMenu;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitSensor;
import com.dwinovo.popularbiology.init.InitActivity;
import com.dwinovo.popularbiology.init.InitCapabilities;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(PopularBiology.MODID)
public class PopularBiology {
    
    public static final String MODID = "popularbiology";

    public static final Logger LOGGER = LogUtils.getLogger();


    public PopularBiology(IEventBus modEventBus, ModContainer modContainer) {

        InitEntity.register(modEventBus);
        InitMemory.register(modEventBus);
        InitMenu.register(modEventBus);
        InitRegistry.register(modEventBus);
        InitSensor.register(modEventBus);
        InitActivity.register(modEventBus);
        InitCapabilities.register(modEventBus);

        modEventBus.addListener(InitEntity::registerAttributes);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }



}
