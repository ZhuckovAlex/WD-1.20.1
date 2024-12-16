package ru.imaginaerum.wd.common.blocks.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.blocks.BlocksWD;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WD.MODID);

    public static final RegistryObject<BlockEntityType<RoseMurdererBlockEntity>> ROSE_MURDERER =
            BLOCK_ENTITIES.register("rose_murderer", () ->
                    BlockEntityType.Builder.of(RoseMurdererBlockEntity::new,
                            BlocksWD.ROSE_OF_THE_MURDERER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DragoliteCageBlockEntity>> DRAGOLITE_CAGE_ENTITY =
            BLOCK_ENTITIES.register("dragolite_cage_entity", () ->
                    BlockEntityType.Builder.of(DragoliteCageBlockEntity::new,
                            BlocksWD.DRAGOLITE_CAGE.get()).build(null));
}