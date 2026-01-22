package com.dwinovo.chiikawa.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.Path;
import com.dwinovo.chiikawa.entity.AbstractPet;
import com.dwinovo.chiikawa.init.InitTag;

// Utility helpers.
public class Utils {

    /**
     * Returns true if the crop at the position can be harvested.
     * @param world the server world
     * @param pos the block position
     * @return whether the crop is harvestable
     */ 
    public static boolean canHarvesr(ServerLevel world,BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        boolean isCrop = state.is(InitTag.ENTITY_HARVEST_CROPS);
        boolean isMaxAge = state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state);
        boolean isMelonOrPumpkin = state.is(Blocks.MELON) || state.is(Blocks.PUMPKIN);
        if(isCrop&&(isMaxAge||isMelonOrPumpkin)){
            return true;
        }
        return false;
    }
    /**
     * Finds a seed stack in the pet backpack.
     * @param pet the pet
     * @return the first seed stack or empty
     */
    public static ItemStack getSeed(AbstractPet pet) {
        for(int i = 0; i < pet.getBackpack().getContainerSize(); i++) {
            ItemStack item = pet.getBackpack().getItem(i);
            if(item.is(InitTag.ENTITY_PLANT_CROPS)) {
                return item;
            }
        }
        return ItemStack.EMPTY;
    }
    /**
     * Finds an arrow stack in the pet backpack.
     * @param pet the pet
     * @return the first arrow stack or empty
     */
    public static ItemStack getArrow(AbstractPet pet) {
        for (int i = 0; i < pet.getBackpack().getContainerSize(); i++) {
            ItemStack item = pet.getBackpack().getItem(i);
            if (ProjectileWeaponItem.ARROW_ONLY.test(item)) {
                return item;
            }
        }
        return ItemStack.EMPTY;
    }
    /**
     * Checks whether the entity can path to the position.
     * @param entity the pet entity
     * @param pos the target position
     * @return whether the position is reachable
     */
    public static boolean canReach(AbstractPet entity, BlockPos pos) {
        PathNavigation navigation = entity.getNavigation();
        Path path = navigation.createPath(pos, 0);
        return path != null && path.canReach();
    }
    /**
     * Checks if the position is plantable farmland.
     * @param world the server world
     * @param pos the block position
     * @return whether a crop can be planted here
     */
    public static boolean isCanPlantFarmland(ServerLevel world, BlockPos pos) {
        return world.getBlockState(pos).is(Blocks.FARMLAND) && world.getBlockState(pos.above()).isAir();
    }
}

