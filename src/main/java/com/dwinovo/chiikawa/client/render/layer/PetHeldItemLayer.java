package com.dwinovo.chiikawa.client.render.layer;

import com.dwinovo.chiikawa.entity.AbstractPet;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.math.Axis;
import software.bernie.geckolib.cache.object.GeoBone;
import net.minecraft.client.renderer.MultiBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.renderer.GeoRenderer;
// Renders items held by pets.
public class PetHeldItemLayer<T extends AbstractPet> extends BlockAndItemGeoLayer<T> {
    private static final String RIGHT_HAND_BONE = "RightHandLocator";
    // Set up the held-item layer.
    public PetHeldItemLayer(GeoRenderer<T> renderer) {
        super(renderer);
    }
    /**
     * Returns the held item for a bone.
     * @param bone the bone
     * @param animatable the pet
     * @return the held item stack
     */
    @Override
    public ItemStack getStackForBone(GeoBone bone, T animatable) {
        return RIGHT_HAND_BONE.equals(bone.getName()) ? animatable.getMainHandItem() : ItemStack.EMPTY;
    }
    /**
     * Returns the display context for the held item.
     * @param bone the bone
     * @param stack the item stack
     * @param animatable the pet
     * @return the display context
     */
    @Override
    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
        return RIGHT_HAND_BONE.equals(bone.getName()) ? 
            ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : 
            ItemDisplayContext.NONE;
    }
    /**
     * Renders the held item.
     * @param poseStack the pose stack
     * @param bone the bone
     * @param stack the item stack
     * @param animatable the pet
     */
    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, T animatable,
                                     MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
        // Apply base scale and item-specific transforms.
        poseStack.scale(0.80f, 0.80f, 0.80f);
        if (stack.getItem() instanceof SwordItem || stack.getItem() instanceof HoeItem) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        if (stack.getItem() instanceof BowItem) {
            poseStack.translate(
                0.10F,  
                -0.20F, 
                -0.10F
            );
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
    }
}

