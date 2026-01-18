package com.dwinovo.popularbiology.entity.brain.sensor;

import net.minecraft.world.entity.ai.sensing.Sensor;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;

import net.minecraft.server.level.ServerLevel;
import java.util.Set;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import com.dwinovo.popularbiology.init.InitMemory;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.utils.BlockSearch;
import com.dwinovo.popularbiology.utils.Utils;

import net.minecraft.core.GlobalPos;

// 这个类用于检测周围是否有可以收获的作物
public class PetHarvestCropSensor extends Sensor<AbstractPet>{
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
            InitMemory.HARVEST_POS.get()
        );
     }

    @Override
    protected void doTick(ServerLevel level, AbstractPet entity) {
        //只有当处于工作状态，并且职业为农民，并且Action为0，才进行检测
        if(entity.getPetMode() == PetMode.WORK && entity.getPetJobId() == InitRegistry.FARMER_ID){
            // 螺旋式检测周围是否有可以收获的作物，要求符合InitTag.ENTITY_HARVEST_CROP标签，并且可以到达
            BlockSearch.spiralBlockSearch(level, entity, MAX_RADIUS, VERTICAL_RANGE,
                (lvl, pos, pet) -> 
                    //pos位置的方块必须是InitTag里面的
                    Utils.canHarvesr(lvl, pos) && Utils.canReach(pet, pos)
                    //如果符合条件，则返回pos位置
            ).ifPresentOrElse(foundPos -> {
                //设置记忆
                entity.getBrain().setMemory(InitMemory.HARVEST_POS.get(), foundPos);
                entity.getBrain().setMemory(MemoryModuleType.HOME, GlobalPos.of(level.dimension(), foundPos));
            }, () -> {
                //否则清除记忆
                entity.getBrain().eraseMemory(InitMemory.HARVEST_POS.get());
            });
        }
    }
}
