package com.dwinovo.popularbiology.entity.brain.sensor;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.utils.BlockSearch;
import com.dwinovo.popularbiology.utils.Utils;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

// 这个类用于检测周围是否有可以种植的耕地
public class PetPlantCropSensor extends Sensor<AbstractPet> {
    // 最大半径
    private static final int MAX_RADIUS = 5;
    // 垂直范围
    private static final int VERTICAL_RANGE = 1;

    /**
     * 这个函数用于获取需要检测的记忆类型
     * @return: 需要检测的记忆类型
     */
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            InitMemory.PLANT_POS.get()
        );
     }

    /**
     * 这个函数用于检测周围是否有可以种植的耕地
     * @param level: 当前世界
     * @param pet: 当前生物
     */
    @Override
    protected void doTick(ServerLevel level, AbstractPet pet) {
        //只有在工作状态，并且职业为农民，并且背包里面有种子，才进行检测
        boolean hasSeed = !Utils.getSeed(pet).isEmpty();
        if(pet.getPetMode() == PetMode.WORK && pet.getPetJobId() == InitRegistry.FARMER_ID
            && hasSeed){
            // 有收获任务时不检测
            if (pet.getBrain().getMemory(InitMemory.HARVEST_POS.get()).isPresent()) {
                return;
            }
            // 螺旋式检测周围是否有可以种植的耕地
            BlockSearch.spiralBlockSearch(level, pet, MAX_RADIUS, VERTICAL_RANGE,
                (lvl, pos, entity) ->
                    // 符合种植条件
                    Utils.isCanPlantFarmland(lvl, pos)
                    // 背包里面有种子
                    && hasSeed
                    // 位置可以到达
                    && Utils.canReach(entity, pos)
                    // 符合条件，则返回pos位置
            ).ifPresentOrElse(foundPos -> {
                // 设置记忆
                pet.getBrain().setMemory(InitMemory.PLANT_POS.get(), foundPos);
            }, () -> {
                // 否则清除记忆
                pet.getBrain().eraseMemory(InitMemory.PLANT_POS.get());
            });
        }
    }
}
