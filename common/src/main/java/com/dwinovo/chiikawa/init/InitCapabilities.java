package com.dwinovo.chiikawa.init;

import com.dwinovo.chiikawa.platform.Services;

public final class InitCapabilities {
    private InitCapabilities() {
    }

    public static void init() {
    }

    public static void register(Object eventBus) {
        Services.CAPABILITY.registerToEventBus(eventBus);
    }
}
