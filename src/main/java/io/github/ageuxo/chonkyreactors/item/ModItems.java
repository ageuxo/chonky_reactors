package io.github.ageuxo.chonkyreactors.item;

import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final  DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ChonkyReactors.MODID);

    public static final RegistryObject<Item> RAW_PHLOGITE = ITEMS.register("raw_phlogite",
            ()-> new Item(new Item.Properties().fireResistant()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
