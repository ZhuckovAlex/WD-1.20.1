package ru.imaginaerum.wd.common.items.arrows;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import ru.imaginaerum.wd.WD;

public class FlameArrowRenderer extends ArrowRenderer<FlameArrow> {
    public static final ResourceLocation FLAME_ARROW = new ResourceLocation(WD.MODID, "textures/entity/projectiles/flame_arrow.png");

    public FlameArrowRenderer(EntityRendererProvider.Context p_174399_) {
        super(p_174399_);
    }

    public ResourceLocation getTextureLocation(FlameArrow p_116001_) {
        return FLAME_ARROW;
    }
}