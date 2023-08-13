package com.github.alexthe666.alexsmobs.client.render.item;

import com.github.alexthe666.citadel.forge.extensions.IClientItemExtensions;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.fabricators_of_create.porting_lib.client.armor.ArmorRenderer;
import io.github.fabricators_of_create.porting_lib.util.client.ClientHooks;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CustomArmorRenderer implements ArmorRenderer {
    @Override
    public void render(PoseStack poseStack, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel, HumanoidModel<LivingEntity> armorModel) {
        var buffer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.armorCutoutNoCull(ClientHooks.getArmorResource(entity, stack, slot, null)), false, stack.hasFoil());
        var model = (HumanoidModel<?>) IClientItemExtensions.of(stack).getGenericArmorModel(entity, stack, slot, contextModel);

        ClientHooks.setPartVisibility(model, slot);

        //contextModel.renderToBuffer(matrices, buffer, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        model.renderToBuffer(poseStack, buffer, light, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
    }
}
