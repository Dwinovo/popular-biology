package com.dwinovo.chiikawa.platform;

import com.dwinovo.chiikawa.platform.services.ICapabilityHelper;

public class FabricCapabilityHelper implements ICapabilityHelper {
    @Override
    public void registerToEventBus(Object eventBus) {
        // Fabric doesn't use NeoForge capabilities.
    }
}
