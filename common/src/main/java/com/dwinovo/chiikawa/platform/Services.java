package com.dwinovo.chiikawa.platform;

import com.dwinovo.chiikawa.platform.services.IPlatformHelper;
import java.util.ServiceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Services {
    private static final Logger LOG = LoggerFactory.getLogger("Chiikawa/Services");

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    private static <T> T load(Class<T> clazz) {
        var loadedService = ServiceLoader.load(clazz)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
