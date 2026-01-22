package com.dwinovo.chiikawa.data;

import com.dwinovo.chiikawa.Chiikawa;
import com.dwinovo.chiikawa.init.InitItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public final class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> itemLookup = this.registries.lookupOrThrow(Registries.ITEM);
        Holder<Enchantment> fireAspect = this.registries.lookupOrThrow(Registries.ENCHANTMENT)
            .get(Enchantments.FIRE_ASPECT)
            .orElseThrow();
        Holder<Enchantment> knockback = this.registries.lookupOrThrow(Registries.ENCHANTMENT)
            .get(Enchantments.KNOCKBACK)
            .orElseThrow();
        ItemStack result = new ItemStack(InitItems.USAGI_WEAPON.get());
        result.enchant(fireAspect, 1);

        ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, result)
            .define('Y', Items.YELLOW_WOOL)
            .define('F', Items.FLINT)
            .define('S', Items.STICK)
            .pattern("  Y")
            .pattern("FSF")
            .pattern("Y  ")
            .unlockedBy(getHasName(Items.YELLOW_WOOL), has(Items.YELLOW_WOOL))
            .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "usagi_weapon")));

        ItemStack hachiwareResult = new ItemStack(InitItems.HACHIWARE_WEAPON.get());
        hachiwareResult.enchant(knockback, 1);
        ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, hachiwareResult)
            .define('B', Items.BLUE_WOOL)
            .define('S', Items.STICK)
            .pattern(" B ")
            .pattern(" SB")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.BLUE_WOOL), has(Items.BLUE_WOOL))
            .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "hachiware_weapon")));

        ItemStack chiikawaResult = new ItemStack(InitItems.CHIIKAWA_WEAPON.get());
        chiikawaResult.enchant(knockback, 1);
        ShapedRecipeBuilder.shaped(itemLookup, RecipeCategory.COMBAT, chiikawaResult)
            .define('P', Items.PINK_WOOL)
            .define('S', Items.STICK)
            .pattern(" P ")
            .pattern(" SP")
            .pattern("S  ")
            .unlockedBy(getHasName(Items.PINK_WOOL), has(Items.PINK_WOOL))
            .save(this.output, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Chiikawa.MODID, "chiikawa_weapon")));
    }
}
