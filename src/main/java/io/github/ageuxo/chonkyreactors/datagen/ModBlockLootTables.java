package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.block.ModBlocks;
import io.github.ageuxo.chonkyreactors.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ASSEMBLY_BLOCK.get());
        dropSelf(ModBlocks.REACTOR_CONTROLLER_BLOCK.get());
        dropSelf(ModBlocks.PLATING_TIER_1.get());

        add(ModBlocks.PHLOGITE_ORE_BLOCK.get(),
                (block)-> createVariantOreDrop(block, ModItems.RAW_PHLOGITE.get(), 2f, 5f));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected @NotNull LootTable.Builder createVariantOreDrop(@NotNull Block pBlock, @NotNull Item pItem, float countMin, float countMax) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(pItem)
                                .apply(SetItemCountFunction
                                .setCount(UniformGenerator.between(countMin, countMax)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
