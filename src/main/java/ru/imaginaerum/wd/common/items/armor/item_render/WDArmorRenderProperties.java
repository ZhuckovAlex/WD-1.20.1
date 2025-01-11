package ru.imaginaerum.wd.common.items.armor.item_render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.client.WDRenderTypes;
import ru.imaginaerum.wd.common.items.armor.ModArmorMaterials;
import ru.imaginaerum.wd.common.items.armor.model_layered.MagicArmorModel;
import ru.imaginaerum.wd.common.items.armor.model_layered.WDModelLayers;
import ru.imaginaerum.wd.common.items.custom.MagicHat;
import ru.imaginaerum.wd.common.items.custom.MagicHatJam;

public class WDArmorRenderProperties implements IClientItemExtensions {

    private static final ResourceLocation MAGIC_ARMOR_GLOW = new ResourceLocation(WD.MODID, "textures/armor/magic_glow/magic_armor_glow.png");
    private static boolean init;
    public static MagicArmorModel MAGIC_ARMOR_MODEL;

    public static void initializeModels() {
        init = true;
        MAGIC_ARMOR_MODEL = new MagicArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(WDModelLayers.MAGIC_ARMOR));
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
        if (!init) {
            initializeModels();
        }
        if (itemStack.getItem() instanceof MagicHat) {
            return MAGIC_ARMOR_MODEL;
        }
        if (itemStack.getItem() instanceof MagicHatJam) {
            return MAGIC_ARMOR_MODEL;
        }
        return _default;
    }

    public static void renderCustomArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ItemStack itemStack, ArmorItem armorItem, Model armorModel, boolean legs, ResourceLocation texture) {
        if(armorItem.getMaterial() == ModArmorMaterials.MAGIC){
            VertexConsumer vertexconsumer1 = itemStack.hasFoil() ? VertexMultiConsumer.create(multiBufferSource.getBuffer(RenderType.entityGlintDirect()), multiBufferSource.getBuffer(RenderType.entityTranslucent(texture))) : multiBufferSource.getBuffer(RenderType.entityTranslucent(texture));
            armorModel.renderToBuffer(poseStack, vertexconsumer1, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        if(armorItem.getMaterial() == ModArmorMaterials.MAGIC_JAM){
            VertexConsumer vertexconsumer1 = itemStack.hasFoil() ? VertexMultiConsumer.create(multiBufferSource.getBuffer(RenderType.entityGlintDirect()), multiBufferSource.getBuffer(RenderType.entityTranslucent(texture))) : multiBufferSource.getBuffer(RenderType.entityTranslucent(texture));
            armorModel.renderToBuffer(poseStack, vertexconsumer1, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            VertexConsumer vertexconsumer2 = multiBufferSource.getBuffer(WDRenderTypes.getEyesAlphaEnabled(MAGIC_ARMOR_GLOW));
            armorModel.renderToBuffer(poseStack, vertexconsumer2, 240, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

    }
}
