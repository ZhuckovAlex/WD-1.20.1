package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class WDSmithingTemplateItem extends SmithingTemplateItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "wd");

    public WDSmithingTemplateItem(
            Component displayName,
            Component baseSlotDescription,
            Component addSlotDescription,
            Component baseSlotTooltip,
            Component addSlotTooltip,
            List<ResourceLocation> baseSlotIcons,
            List<ResourceLocation> addSlotIcons
    ) {
        super(displayName, baseSlotDescription, addSlotDescription, baseSlotTooltip, addSlotTooltip, baseSlotIcons, addSlotIcons);
    }


}
