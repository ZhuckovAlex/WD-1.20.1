package ru.imaginaerum.wd.common.items.entity.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import ru.imaginaerum.wd.WD;

public class ModModelLayersItem {

    public static final ModelLayerLocation APPLE_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(WD.MODID, "boat/apple"), "main");
    public static final ModelLayerLocation APPLE_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(WD.MODID, "chest_boat/apple"), "main");

}