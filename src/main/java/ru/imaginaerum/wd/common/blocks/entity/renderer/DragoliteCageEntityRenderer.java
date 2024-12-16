package ru.imaginaerum.wd.common.blocks.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import ru.imaginaerum.wd.common.blocks.entity.DragoliteCageBlockEntity;

import java.awt.*;
import java.util.Optional;

public class DragoliteCageEntityRenderer implements BlockEntityRenderer<DragoliteCageBlockEntity> {

    public DragoliteCageEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DragoliteCageBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // Получаем значение из тега soul_first
        String soulFirst = blockEntity.getSoulFirst();

        // Если значение не пустое
        if (soulFirst != null && !soulFirst.isEmpty()) {
            // Преобразуем soulFirst в EntityType с помощью ResourceLocation
            Optional<EntityType<?>> entityTypeOptional = EntityType.byString(soulFirst);

            // Если сущность с таким типом существует
            if (entityTypeOptional.isPresent()) {
                // Создаем сущность на основе найденного типа
                Entity entity = entityTypeOptional.get().create(Minecraft.getInstance().level);

                if (entity != null) {
                    // Получаем рендерер для этой сущности
                    EntityRenderer<? super Entity> entityRenderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);

                    // Позиционируем сущность немного выше и рядом с блоком
                    poseStack.pushPose();
                    poseStack.translate(0.5D, 1.0D, 0.5D); // Позиция для сущности (немного выше блока)

                    // Рендерим сущность с использованием рендерера
                    entityRenderer.render(entity, 0, partialTick, poseStack, bufferSource, packedLight);
                    poseStack.popPose();
                }
            }
        }
    }

}