package com.alexlightovich.shawrmamod.block.entity;

import com.alexlightovich.shawrmamod.ModBlocks;
import com.alexlightovich.shawrmamod.ShawrmaMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ShawrmaMod.MODID);

    public static final RegistryObject<BlockEntityType<VertelBlockEntity>> VERTEL_BLOCK = BLOCK_ENTITIES.register("vertel_block_entity", () -> BlockEntityType.Builder.of(VertelBlockEntity::new, ModBlocks.VERTEL.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
