package ru.imaginaerum.wd.common.items.armor.model_layered;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class MagicArmorModel extends HumanoidModel {

    public ModelPart group;
    public MagicArmorModel(ModelPart root) {
        super(root);
        this.group = root.getChild("head").getChild("group");
    }

    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");

        PartDefinition group = head.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.4767F, -12.5005F, 0.5768F, -0.0349F, -0.0175F, 0.0F));

        PartDefinition hat4_r1 = group.addOrReplaceChild("hat4_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-0.6F, -0.2F, -0.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-0.2233F, -5.2995F, 0.6732F, -0.2932F, -0.0366F, -0.1212F));

        PartDefinition hat3_r1 = group.addOrReplaceChild("hat3_r1", CubeListBuilder.create().texOffs(0, 38).addBox(-2.0F, -1.4F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0267F, -2.0995F, 0.1732F, -0.118F, -0.0458F, -0.0727F));

        PartDefinition hat2_r1 = group.addOrReplaceChild("hat2_r1", CubeListBuilder.create().texOffs(21, 21).addBox(-3.5F, -2.0F, -3.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2767F, 1.9005F, -0.3268F, -0.033F, -0.0375F, -0.0381F));

        PartDefinition headwear_r1 = group.addOrReplaceChild("headwear_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-7.0F, 1.0F, -7.0F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(7.0F, 1.0F, -7.0F, 0.0F, 6.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 32).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-7.0F, 1.0F, 7.0F, 14.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2767F, 4.5005F, -0.5768F, -0.0165F, -0.0355F, -0.0187F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }


}
