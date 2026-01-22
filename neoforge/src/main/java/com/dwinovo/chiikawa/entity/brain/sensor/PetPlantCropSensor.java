package com.dwinovo.chiikawa.entity.brain.sensor;

import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.entity.PetMode;
import com.dwinovo.chiikawa.init.InitMemory;
import com.dwinovo.chiikawa.init.InitRegistry;
import com.dwinovo.chiikawa.utils.BlockSearch;
import com.dwinovo.chiikawa.utils.Utils;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

// Finds nearby farmland for planting.
public class PetPlantCropSensor extends Sensor<AbstractPet> {
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
            InitMemory.PLANT_POS.get()
        );
     }

    /**
     * Scan for plantable farmland.
     * @param level the server level
     * @param pet the pet entity
     */
    @Override
    protected void doTick(ServerLevel level, AbstractPet pet) {
        boolean hasSeed = !Utils.getSeed(pet).isEmpty();
        if (pet.getPetMode() != PetMode.WORK || pet.getPetJobId() != InitRegistry.FARMER_ID || !hasSeed) {
            pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
            return;
        }
        if (pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).isPresent()) {
            net.minecraft.core.BlockPos current = pet.getBrain().getMemory(InitMemory.PLANT_POS.get()).get();
            if (Utils.isCanPlantFarmland(level, current) && Utils.canReach(pet, current)) {
                return;
            }
            pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
        }
        if(pet.getPetMode() == PetMode.WORK && pet.getPetJobId() == InitRegistry.FARMER_ID
            && hasSeed){
            // Skip if a harvest target exists.
            if (pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
                return;
            }
            // Spiral search for reachable, plantable farmland.
            BlockSearch.spiralBlockSearch(level, pet, MAX_RADIUS, VERTICAL_RANGE,
                (lvl, pos, entity) ->
                    Utils.isCanPlantFarmland(lvl, pos)
                    && hasSeed
                    && Utils.canReach(entity, pos)
            ).ifPresentOrElse(foundPos -> {
                pet.getBrain().setMemory(InitMemory.PLANT_POS.get(), foundPos);
            }, () -> {
                pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
            });
        }
    }
}

