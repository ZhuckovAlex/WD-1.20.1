package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.server.item.CustomArmorPostRender;
import ru.imaginaerum.wd.server.item.KeybindUsingArmor;
import ru.imaginaerum.wd.server.item.UpdatesStackTags;

import javax.annotation.Nullable;

public class MagicHat extends ArmorItem implements CustomArmorPostRender, KeybindUsingArmor, UpdatesStackTags {
    public MagicHat(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }
    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) WD.PROXY.getArmorProperties());
    }
    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return WD.MODID + ":textures/armor/magic_hat.png";
    }
    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return true;
    }


    @Override
    public void onKeyPacket(Entity keyPresser, ItemStack itemStack, int keyType) {

    }
}
