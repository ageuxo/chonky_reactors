package io.github.ageuxo.chonkyreactors.multiblock;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.ChonkyReactors;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MultiblockProvider extends DatapackBuiltinEntriesProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    public MultiblockProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Multiblocks.BUILDER,  Set.of(ChonkyReactors.MODID));
    }

    @Override
    public @NotNull String getName() {
        return MultiblockRegistry.KEY.location().toString();
    }
}
