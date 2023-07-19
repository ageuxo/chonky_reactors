package io.github.ageuxo.chonkyreactors.multiblock;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MultiblockRegistry {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final List<String> CACHE = new ArrayList<>();

    public static final ResourceKey<Registry<MultiblockDefinition>> KEY = ResourceKey.createRegistryKey(ModUtils.modRL("multiblocks"));

    public static final DeferredRegister<MultiblockDefinition> MB_DEFS = DeferredRegister.create(ModUtils.modRL("multiblocks"), ChonkyReactors.MODID);



    public static List<String> getMultiblockRLs(Registry<MultiblockDefinition> registry){
        if (CACHE.isEmpty()) {
            registry.keySet().forEach(rl -> CACHE.add(rl.toString() ));
        }
        CACHE.sort(Comparator.naturalOrder());
        return CACHE;
    }

    public static List<String> getMultiblockRLs(Registry<MultiblockDefinition> registry, int fromIndex, int toIndex){
        return getMultiblockRLs(registry).subList(fromIndex, toIndex);
    }
}
