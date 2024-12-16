package ru.imaginaerum.wd.common.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DragoliteCageBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private String soulFirst = "bat";
    private String soulSecond = "";

    public DragoliteCageBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRAGOLITE_CAGE_ENTITY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public ItemStack removeItem(int i, int i1) {
        return null;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return null;
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
    }

    @Override
    public boolean stillValid(Player player) {
        return false;
    }

    @Override
    public void clearContent() {
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("soul_first", soulFirst);  // Сохраняем первый дух (сущность)
        tag.putString("soul_second", soulSecond);  // Сохраняем второй дух (сущность)
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        soulFirst = tag.getString("soul_first");  // Загружаем первый дух (сущность)
        soulSecond = tag.getString("soul_second");  // Загружаем второй дух (сущность)
    }
    public String getSoulFirst() {
        return soulFirst;
    }

    public void setSoulFirst(String soulFirst) {
        this.soulFirst = soulFirst;
    }

    public String getSoulSecond() {
        return soulSecond;
    }

    public void setSoulSecond(String soulSecond) {
        this.soulSecond = soulSecond;
    }
}
