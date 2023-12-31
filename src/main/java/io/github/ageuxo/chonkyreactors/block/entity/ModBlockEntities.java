package io.github.ageuxo.chonkyreactors.block.entity;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChonkyReactors.MODID);

    public static final RegistryObject<BlockEntityType<AssemblyBlockEntity>> ASSEMBLY_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("assembly_block_entity", ()->BlockEntityType.Builder.of(AssemblyBlockEntity::new,
                                                    ModBlocks.ASSEMBLY_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ReactorBlockEntity>> REACTOR_BLOCK_ENTITY = BLOCK_ENTITIES
            .register("reactor_block_entity", ()->BlockEntityType.Builder.of(ReactorBlockEntity::new,
                    ModBlocks.REACTOR_CONTROLLER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<ReactorSubBlockEntity>> REACTOR_SUB_ENTITY = BLOCK_ENTITIES
            .register("reactor_sub_block_entity", ()->BlockEntityType.Builder.of(ReactorSubBlockEntity::new,
                    ModBlocks.REACTOR_SUB_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
