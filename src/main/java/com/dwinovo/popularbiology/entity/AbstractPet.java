package com.dwinovo.popularbiology.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.nbt.CompoundTag;
import com.mojang.serialization.Dynamic;
import com.dwinovo.popularbiology.PopularBiology;
import com.dwinovo.popularbiology.entity.interact.PetInteractHandler;
import com.dwinovo.popularbiology.entity.job.api.IPetJob;
import com.dwinovo.popularbiology.init.InitRegistry;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractPet extends TamableAnimal implements GeoEntity {
    public static final int BACKPACK_SIZE = 16;
    private static final EntityDataAccessor<Byte> PET_MODE = SynchedEntityData.defineId(AbstractPet.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> PET_JOB = SynchedEntityData.defineId(AbstractPet.class, EntityDataSerializers.INT);
    private static final java.util.List<MemoryModuleType<?>> MEMORY_TYPES = java.util.List.of(
        MemoryModuleType.PATH,
        MemoryModuleType.DOORS_TO_CLOSE,
        MemoryModuleType.LOOK_TARGET,
        MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
        MemoryModuleType.WALK_TARGET,
        MemoryModuleType.ATTACK_TARGET,
        MemoryModuleType.ATTACK_COOLING_DOWN,
        MemoryModuleType.HURT_BY_ENTITY,
        MemoryModuleType.HOME
    );
    private static final java.util.List<net.minecraft.world.entity.ai.sensing.SensorType<? extends net.minecraft.world.entity.ai.sensing.Sensor<? super AbstractPet>>> SENSOR_TYPES = java.util.List.of(
        net.minecraft.world.entity.ai.sensing.SensorType.HURT_BY,
        net.minecraft.world.entity.ai.sensing.SensorType.NEAREST_LIVING_ENTITIES,
        com.dwinovo.popularbiology.init.InitSensor.PET_ATTACKBLE_ENTITY_SENSOR.get()
    );
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private final SimpleContainer backpack = new SimpleContainer(BACKPACK_SIZE);

    protected AbstractPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        backpack.addListener(new ContainerListener() {
            @Override
            public void containerChanged(Container container) {
                refreshJobFromMainhand();
            }
        });
    }

    public SimpleContainer getBackpack() {
        return backpack;
    }

    public PetMode getPetMode() {
        return PetMode.fromId(this.entityData.get(PET_MODE));
    }

    public void setPetMode(PetMode mode) {
        this.entityData.set(PET_MODE, (byte) mode.ordinal());
    }

    public int getPetJobId() {
        return this.entityData.get(PET_JOB);
    }

    public void setPetJobId(int jobId) {
        this.entityData.set(PET_JOB, jobId);
    }

    public void refreshJobFromMainhand() {
        if (level().isClientSide) {
            return;
        }
        
        IPetJob best = null;
        int bestPriority = Integer.MIN_VALUE;
        for (IPetJob job : InitRegistry.PET_JOB_REGISTRY) {
            if (!job.canAssume(this)) {
                continue;
            }
            int priority = job.getPriority();
            if (best == null || priority > bestPriority) {
                best = job;
                bestPriority = priority;
            }
        }
        if (best == null) {
            best = InitRegistry.NONE.get();
        }
        int newJobId = best.getId();
        if (newJobId != getPetJobId()) {
            setPetJobId(newJobId);
            refreshBrain((ServerLevel)this.level());
        }
    }

    private void refreshBrain(ServerLevel serverLevelIn) {
        Brain<AbstractPet> brain = this.getBrain();
        brain.stopAll(serverLevelIn, this);
        //复制Brain
        Brain<AbstractPet> newBrain = brain.copyWithoutBehaviors();
        this.brain = newBrain;
        //初始化新AI行为
        InitRegistry.getJobFromId(getPetJobId()).initBrain(this, newBrain);
    }

    @Override
    protected Brain.Provider<AbstractPet> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<AbstractPet> brain = (Brain<AbstractPet>) brainProvider().makeBrain(dynamic);
        InitRegistry.getJobFromId(getPetJobId()).initBrain(this, brain);
        return brain;
    }

    @Override
    protected void customServerAiStep() {
        if (tickCount % 40 == 0) {
            PopularBiology.LOGGER.debug(
                "[PetBrain] {} jobId={} side=server",
                getStringUUID(),
                getPetJobId()
            );
        }
        InitRegistry.getJobFromId(getPetJobId()).tickBrain(this,this.getBrain());
        Brain<AbstractPet> brain = (Brain<AbstractPet>) getBrain();
        brain.tick((ServerLevel) level(), this);
        super.customServerAiStep();
        
    }
    public Brain<AbstractPet> getBrain() {
        return (Brain<AbstractPet>) super.getBrain();
    }


    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return backpack.getItem(0);
        }
        return super.getItemBySlot(slot);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            backpack.setItem(0, stack);
            refreshJobFromMainhand();
            return;
        }
        super.setItemSlot(slot, stack);
    }
    
    @Override
    public boolean canMate(Animal other) {
        return false; // 禁止繁殖
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null; // 保险：不会生成幼体
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<AbstractPet> main = new AnimationController<>(this, "main", 5, state -> {
            RawAnimation builder = RawAnimation.begin();
            if (this.getPetMode() == PetMode.SIT)
            {
                builder.thenLoop("sit");
            }
            else if (state.isMoving()) {
                builder.thenLoop("run");
            }
            else {
                builder.thenLoop("idle");
            }
            state.setAndContinue(builder);
            return PlayState.CONTINUE;
        });

        AnimationController<AbstractPet> sub = new AnimationController<>(this, "sub", 1, state -> {
            return PlayState.STOP;
        });

        controllers.add(main, sub);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animatableInstanceCache;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PET_MODE, (byte) PetMode.FOLLOW.ordinal());
        builder.define(PET_JOB, InitRegistry.NONE_ID);
    }

    @Override
    public boolean isFood(ItemStack arg0) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Backpack", ContainerHelper.saveAllItems(new CompoundTag(), backpack.getItems(), level().registryAccess()));
        tag.putInt("PetJob", getPetJobId());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Backpack")) {
            ContainerHelper.loadAllItems(tag.getCompound("Backpack"), backpack.getItems(), level().registryAccess());
        }
        if (tag.contains("PetJob")) {
            setPetJobId(tag.getInt("PetJob"));
        }
        refreshJobFromMainhand();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result = PetInteractHandler.handle(this, player, hand);
        if (result != InteractionResult.PASS) {
            return result;
        }
        return super.mobInteract(player, hand);
    }
    
}
