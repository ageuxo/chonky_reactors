package io.github.ageuxo.chonkyreactors.item.crafting;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Consumer;

public class AssemblyRecipeBuilder implements RecipeBuilder {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Item result;
    private final int count;
    private final NonNullList<StackIngredient> ingredients = NonNullList.create();
 /*   private TagKey<Item> tagKey;
    private int ingredientCount;*/
    private int energyCost;

    public AssemblyRecipeBuilder(ItemLike outItem, int outCount) {
        this.result = outItem.asItem();
        this.count = outCount;
    }

    public static AssemblyRecipeBuilder builder(ItemLike outItem, int outCount) {
        return new AssemblyRecipeBuilder(outItem, outCount);
    }

    public AssemblyRecipeBuilder requires(ItemLike inItem) {
        return requires(inItem, 1);
    }

    public AssemblyRecipeBuilder requires(ItemLike inItem, int inCount) {
        return requires(new ItemStack(inItem, inCount));
    }

    public AssemblyRecipeBuilder requires(ItemStack stack) {
        this.ingredients.add(new StackIngredient(stack));
        return this;
    }

    public AssemblyRecipeBuilder requires(TagKey<Item> tagKey) {
        return requires(tagKey, 1);
    }

    public AssemblyRecipeBuilder requires(TagKey<Item> tagKey, int ingCount) {
        ITag<Item> itemTag = ForgeRegistries.ITEMS.tags().getTag(tagKey);
        this.ingredients.add(new StackIngredient(itemTag.getKey(), ingCount));
        return this;
    }

    public AssemblyRecipeBuilder requiresEnergy(int energyCost) {
        this.energyCost = energyCost;
        return this;
    }

    @Override
    public @Nullable AssemblyRecipeBuilder unlockedBy(@NotNull String pCriterionName, @NotNull CriterionTriggerInstance pCriterionTrigger) {
        return null; //TODO implement advancements
    }

    @Override
    public @Nullable AssemblyRecipeBuilder group(@Nullable String pGroupName) {
        return null;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result;
    }

    public int getCount() {
        return this.count;
    }

    public int getEnergyCost() {
        return this.energyCost;
    }

    public NonNullList<StackIngredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public void save(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull ResourceLocation pRecipeId) {

        if (!getIngredients().isEmpty()) {
            pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.getResult(), this.getCount(), this.getEnergyCost(), this.getIngredients()));
       /* } else if (getTagKey() != null) {
            pFinishedRecipeConsumer.accept(new Result(pRecipeId, this.getResult(), this.getCount(), this.getEnergyCost(), this.getTagKey()));*/
        } else {
            throw new IllegalStateException("AssemblyRecipeBuilder with invalid result");
        }

    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final NonNullList<StackIngredient> ingredients;
//        private final TagKey<Item> tagKey;
//        private final int tagIngrCount;
        private final int energyCost;

        /*public Result(ResourceLocation id, Item result, int resultCount, int energyCost, TagKey<Item> tagKey, int tagIngrCount) {
            this(id, result, resultCount, energyCost, null, tagKey, tagIngrCount);
        }

        public Result(ResourceLocation id, Item result, int resultCount, int energyCost, ArrayList<ItemStack> ingredients) {
            this(id, result, resultCount, energyCost, ingredients, null, 0);
        }*/

        public Result(ResourceLocation id, Item result, int resultCount, int energyCost, NonNullList<StackIngredient> ingredients) {
            this.id = id;
            this.result = result;
            this.count = resultCount;
            this.ingredients = ingredients;
//            this.tagIngrCount = tagIngrCount;
//            this.tagKey = tagKey;
            this.energyCost = energyCost;
        }

        @Override
        public void serializeRecipeData(@NotNull JsonObject pJson) {
        /*    JsonArray jsonArray = new JsonArray();

            if (this.ingredients != null && !this.ingredients.isEmpty()) {
                for (StackIngredient ingredient : this.ingredients) {
                    jsonArray.add(ingredient.toJson());
                }
            } else if (this.tagKey != null && this.tagIngrCount > 1) {
                jsonArray.add(new StackIngredient(this.tagKey, this.tagIngrCount).toJson());
            }

            pJson.add("ingredients", jsonArray);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            jsonObject.addProperty("count", this.count);

            pJson.add("result", jsonObject);
            pJson.add("energy_cost", new JsonPrimitive(this.energyCost));*/
            var entries = pJson.entrySet();

            AssemblyRecipe recipe = new AssemblyRecipe(this.id, new ItemStack(this.result, this.count), this.ingredients, this.energyCost);
            JsonObject recipeJson = AssemblyRecipe.CODEC.encodeStart(JsonOps.INSTANCE, recipe).getOrThrow(false, LOGGER::error).getAsJsonObject();
            recipeJson.entrySet().forEach( (entry)-> pJson.add( entry.getKey(), entry.getValue() ));


/*            for (Map.Entry<String, JsonElement> entry : entries) {
                pJson.add(entry.getKey(), entry.getValue());
            }*/

        }

        @Override
        public @NotNull ResourceLocation getId() {
            return this.id;
        }

        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return ModRecipes.ASSEMBLY_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }

}
