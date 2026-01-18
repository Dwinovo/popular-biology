package com.dwinovo.popularbiology.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.pathfinder.Path;
import com.dwinovo.popularbiology.entity.AbstractPet;
import com.dwinovo.popularbiology.init.InitTag;

// 这个类是一些工具函数
public class Utils {

    /**
     * 这个函数用于判断一个位置的作物是否可以收获
     * @param world: 世界
     * @param pos: 位置
     * @return: 是否可以收获
     */ 
    public static boolean canHarvesr(ServerLevel world,BlockPos pos) {
        // 获得方块状态
        BlockState state = world.getBlockState(pos);
        // 判断是否是作物
        boolean isCrop = state.is(InitTag.ENTITY_HARVEST_CROPS);
        // 判断是否成熟
        boolean isMaxAge = state.getBlock() instanceof CropBlock crop && crop.isMaxAge(state);
        //处理瓜类作物
        boolean isMelonOrPumpkin = state.is(Blocks.MELON) || state.is(Blocks.PUMPKIN);
        // 如果作物成熟或者瓜类作物，返回true
        if(isCrop&&(isMaxAge||isMelonOrPumpkin)){
            return true;
        }
        // 否则返回false
        return false;
    }
    /**
     * 这个函数用于遍历一个BasePet的Container是否具有种子
     * @param pet: 生物
     * @return: 种子
     */
    public static ItemStack getSeed(AbstractPet pet) {
        // 遍历背包
        for(int i = 0; i < pet.getBackpack().getContainerSize(); i++) {
            // 获得物品
            ItemStack item = pet.getBackpack().getItem(i);
            // 判断是否是种子
            if(item.is(InitTag.ENTITY_PLANT_CROPS)) {
                // 返回种子
                return item;
            }
        }
        return ItemStack.EMPTY;
    }
    /**
     * 这个函数用于遍历宠物背包获取可用的箭矢
     * @param pet: 生物
     * @return: 箭矢
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
     * 这个函数用于判断一个实体是否可以到达一个位置
     * @param entity: 实体
     * @param pos: 位置
     * @return: 是否可以到达
     */
    public static boolean canReach(AbstractPet entity, BlockPos pos) {
        // 获得导航
        PathNavigation navigation = entity.getNavigation();
        // 创建路径
        Path path = navigation.createPath(pos, 0);
        // 判断路径是否存在并且可以到达
        return path != null && path.canReach();
    }
    /**
     * 这个函数用于判断一个位置是否是可以种植作物的耕地
     * @param world: 世界
     * @param pos: 位置
     * @return: 是否可以种植作物
     */
    public static boolean isCanPlantFarmland(ServerLevel world, BlockPos pos) {
        //这个位置必须是耕地，他的上方必须是空气
        return world.getBlockState(pos).is(Blocks.FARMLAND) && world.getBlockState(pos.above()).isAir();
    }
}
