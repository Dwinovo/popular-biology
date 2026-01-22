package com.dwinovo.chiikawa.entity.brain.sensor;

import net.minecraft.world.entity.ai.sensing.Sensor;
import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.entity.PetMode;

import net.minecraft.server.level.ServerLevel;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import com.dwinovo.chiikawa.init.InitMemory;
import com.dwinovo.chiikawa.init.InitRegistry;
import com.dwinovo.chiikawa.utils.BlockSearch;
import com.dwinovo.chiikawa.utils.Utils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

// Finds nearby harvestable crops.
public class PetHarvestCropSensor extends Sensor<AbstractPet>{
    // Search radius.
    private static final int MAX_RADIUS = 5;
    // Vertical search range.
    private static final int VERTICAL_RANGE = 1;
    /**
     * Memory types used by this sensor.
     * @return required memory types
     */
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            InitMemory.HARVEST_POS.get()
        );
     }

    @Override
    protected void doTick(ServerLevel level, AbstractPet entity) {
        if (entity.getPetMode() != PetMode.WORK || entity.getPetJobId() != InitRegistry.FARMER_ID) {
            entity.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
            return;
        }
        if (entity.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
            net.minecraft.core.BlockPos current = entity.getBrain().getMemory(InitMemory.HARVEST_POS.get()).get();
            if (Utils.canHarvesr(level, current) && Utils.canReach(entity, current)) {
                return;
            }
            entity.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
        }
        if(entity.getPetMode() == PetMode.WORK && entity.getPetJobId() == InitRegistry.FARMER_ID){
            // Spiral search for reachable, harvestable crops.
            BlockSearch.spiralBlockSearch(level, entity, MAX_RADIUS, VERTICAL_RANGE,
                (lvl, pos, pet) -> 
                    Utils.canHarvesr(lvl, pos) && Utils.canReach(pet, pos)
            ).ifPresentOrElse(foundPos -> {
                entity.getBrain().setMemory(InitMemory.HARVEST_POS.get(), foundPos);
            }, () -> {
                entity.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
            });
        }
    }
}

