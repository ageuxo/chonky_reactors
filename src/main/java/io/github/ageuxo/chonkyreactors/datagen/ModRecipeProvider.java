package io.github.ageuxo.chonkyreactors.datagen;

import io.github.ageuxo.chonkyreactors.item.crafting.AssemblyRecipeBuilder;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        AssemblyRecipeBuilder.builder(Blocks.IRON_TRAPDOOR, 4)
                .requires(new TagKey<>(ForgeRegistries.ITEMS.getRegistryKey(), ResourceLocation.tryParse("forge:ingots/iron")), 4)
                .requiresEnergy(32)
                .save(consumer, ModUtils.modRL( "connecting_face_block_from_iron_assembly"));

    }

    protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull RecipeCategory category, ItemLike uncompactedItem, @NotNull RecipeCategory compactingCategory, ItemLike compactedItem) {
        nineBlockStorageRecipes(recipeConsumer, category, uncompactedItem, compactingCategory, compactedItem, getSimpleRecipeName(compactedItem), null, getSimpleRecipeName(uncompactedItem), null);
    }

    protected static void nineBlockStorageRecipes(@NotNull Consumer<FinishedRecipe> recipeConsumer, @NotNull RecipeCategory uncompactingCategory, ItemLike uncompactedItem, @NotNull RecipeCategory compactingCategory, ItemLike compactedItem, String compactRL, @Nullable String compactGroup, String uncompactRL, @Nullable String uncompactGroup) {
        ShapelessRecipeBuilder.shapeless(uncompactingCategory, uncompactedItem, 9).requires(compactedItem).group(uncompactGroup).unlockedBy(getHasName(compactedItem), has(compactedItem)).save(recipeConsumer, ModUtils.modRL( uncompactRL));
        ShapedRecipeBuilder.shaped(compactingCategory, compactedItem).define('#', uncompactedItem).pattern("###").pattern("###").pattern("###").group(compactGroup).unlockedBy(getHasName(uncompactedItem), has(uncompactedItem)).save(recipeConsumer, ModUtils.modRL( compactRL));
    }
}
