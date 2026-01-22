package com.dwinovo.chiikawa.client.render.layer;

import com.dwinovo.chiikawa.client.render.AbstractPetRender;
import com.dwinovo.chiikawa.entity.AbstractPet;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.tags.ItemTags;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import com.mojang.blaze3d.vertex.PoseStack;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderer;

import java.util.List;
// Renders items held by pets.
public class PetHeldItemLayer<T extends AbstractPet> extends BlockAndItemGeoLayer<T, Void, AbstractPetRender.PetRenderState> {
    private static final String RIGHT_HAND_BONE = "RightHandLocator";
    private static final DataTicket<ItemStack> HELD_ITEM = DataTicket.create("chiikawa_held_item", ItemStack.class);
    // Set up the held-item layer.
    public PetHeldItemLayer(GeoRenderer<T, Void, AbstractPetRender.PetRenderState> renderer) {
        super(renderer);
    }
    /**
     * Store the held item so it can be accessed during per-bone rendering.
     * @param renderState the render state
     * @param relatedObject unused
     * @param renderState the render state
     */
    @Override
    public void addRenderData(T animatable, Void relatedObject, AbstractPetRender.PetRenderState renderState) {
        renderState.addGeckolibData(HELD_ITEM, animatable.getMainHandItem());
    }
    /**
     * Provide the bones this layer should render for.
     * @param renderState the render state
     * @param model the baked model
     * @return render data per bone
     */
    @Override
    protected List<RenderData<AbstractPetRender.PetRenderState>> getRelevantBones(AbstractPetRender.PetRenderState renderState, BakedGeoModel model) {
        return List.of(new RenderData<>(
                RIGHT_HAND_BONE,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                (bone, state) -> Either.left(state.getOrDefaultGeckolibData(HELD_ITEM, ItemStack.EMPTY))
        ));
    }
    /**
     * Renders the held item.
     * @param poseStack the pose stack
     * @param bone the bone
     * @param stack the item stack
     * @param packedOverlay the pet
     */
    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, ItemDisplayContext displayContext,
                                      AbstractPetRender.PetRenderState renderState, MultiBufferSource bufferSource,
                                      int packedLight, int packedOverlay) {
        // Apply base scale and item-specific transforms.
        poseStack.scale(0.80f, 0.80f, 0.80f);
        if (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.HOES)) {
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        if (stack.is(ItemTags.BOW_ENCHANTABLE)) {
            poseStack.translate(
                    0.10F,
                    -0.20F,
                    -0.10F
            );
            poseStack.mulPose(Axis.XP.rotationDegrees(-90f));
        }
        super.renderStackForBone(poseStack, bone, stack, displayContext, renderState, bufferSource, packedLight, packedOverlay);
    }
}
