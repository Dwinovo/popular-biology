package com.dwinovo.popularbiology.entity.brain.sensor;

import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.entity.PetMode;
import com.dwinovo.popularbiology.init.InitRegistry;
import com.dwinovo.popularbiology.init.InitTag;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.phys.AABB;

public class PetAttackbleEntitySensor extends Sensor<AbstractPet> {
    public PetAttackbleEntitySensor() {
        // 60 tick 检测一次
        super(60);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.HURT_BY_ENTITY
        );
    }

    @Override
    protected void doTick(ServerLevel level, AbstractPet pet) {
        /*
         * 1.是否是WORK状态
         * 2.是否是Fencer或者Archer职业
         * 3.是否存在攻击目标
         * 4.当前的攻击目标是否超过15格
         * 5.当前的攻击目标是否符合InitTag
         */
        if (pet.getPetMode() != PetMode.WORK) {
            pet.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
            return;
        }
        int jobId = pet.getPetJobId();
        if (jobId != InitRegistry.FENCER_ID && jobId != InitRegistry.ARCHER_ID) {
            return;
        }

        pet.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).ifPresent(hurtByEntity -> {
            pet.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, hurtByEntity);
        });

        Optional<LivingEntity> currentTarget = pet.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        if (currentTarget.isPresent()) {
            LivingEntity target = currentTarget.get();
            if (target.isAlive() && target.distanceTo(pet) <= 15.0F) {
                return;
            }
            pet.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        }

        AABB searchBox = pet.getBoundingBox().inflate(15.0);
        List<Entity> nearbyEntities = level.getEntities(pet, searchBox, e ->
            e.getType().is(InitTag.ENTITY_HOSTILE_ENTITY) && e.isAlive());
        Optional<Entity> closestTarget = nearbyEntities.stream()
            .min((e1, e2) -> Double.compare(e1.distanceToSqr(pet), e2.distanceToSqr(pet)));
        closestTarget.ifPresentOrElse(
            target -> pet.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, (LivingEntity) target),
            () -> pet.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET)
        );
    }
}
