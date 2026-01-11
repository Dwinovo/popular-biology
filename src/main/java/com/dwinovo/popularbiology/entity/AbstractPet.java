package com.dwinovo.popularbiology.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import com.dwinovo.popularbiology.menu.PetBackpackMenu;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AbstractPet extends TamableAnimal implements GeoEntity, MenuProvider {
    public static final int BACKPACK_SIZE = 16;
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private final SimpleContainer backpack = new SimpleContainer(BACKPACK_SIZE) {
        @Override
        public void setChanged() {
            super.setChanged();
            if (!level().isClientSide) {
                level().getChunkAt(blockPosition()).setUnsaved(true);
            }
        }
    };

    protected AbstractPet(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public SimpleContainer getBackpack() {
        return backpack;
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
            if (state.isMoving()) {
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
    public boolean isFood(ItemStack arg0) {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.popularbiology.pet_backpack");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new PetBackpackMenu(containerId, inventory, this);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Backpack", ContainerHelper.saveAllItems(new CompoundTag(), backpack.getItems(), level().registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Backpack")) {
            ContainerHelper.loadAllItems(tag.getCompound("Backpack"), backpack.getItems(), level().registryAccess());
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(this);
        }
        return InteractionResult.sidedSuccess(level().isClientSide);
    }
    
}
