package com.dwinovo.chiikawa.platform.services;

public interface IEntityHelper {
    void registerToEventBus(Object eventBus);

    void registerAttributes();

    void registerSpawnPlacements();
}
