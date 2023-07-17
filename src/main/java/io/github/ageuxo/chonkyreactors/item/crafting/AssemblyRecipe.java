package io.github.ageuxo.chonkyreactors.item.crafting;

import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.List;

public class AssemblyRecipe implements Recipe<SimpleMachineContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final List<StackIngredient> recipeIngredients;
    private final int energyCost;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<AssemblyRecipe> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(AssemblyRecipe::getId),
                    ItemStack.CODEC.fieldOf("output").forGetter(AssemblyRecipe::getOutput),
                    StackIngredient.CODEC.listOf().fieldOf("ingredients").forGetter(AssemblyRecipe::getStackIngredients),
                    Codec.INT.fieldOf("energy_cost").forGetter(AssemblyRecipe::getEnergyCost)
            ).apply(instance, AssemblyRecipe::new));

    public AssemblyRecipe(ResourceLocation id, ItemStack output, List<StackIngredient> recipeIngredients,  int energyCost) {
        this(id, output, NonNullList.withSize(recipeIngredients.size(), recipeIngredients.get(0)), energyCost);
    }

    public AssemblyRecipe(ResourceLocation id, ItemStack output, NonNullList<StackIngredient> recipeIngredients, int energyCost){
        this.id = id;
        this.output = output;
        this.recipeIngredients = recipeIngredients;
        this.energyCost = energyCost;
    }


    @Override
    public boolean matches(@NotNull SimpleMachineContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return recipeIngredients.stream().allMatch( (ing) -> {
            int keepCount = 0;
            for (int index : pContainer.getInputSlots()) {
                ItemStack stack = pContainer.getItem(index);
                if (ing.test(stack)){
                    if (stack.getCount() + keepCount >= ing.getCount()){
                        return true;
                    } else {
                        keepCount += stack.getCount();
                    }
                }
            }
            return false;
        });
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleMachineContainer container, @NotNull RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return output.copy();
    }

    public ItemStack getOutput(){
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public List<StackIngredient> getStackIngredients(){
        return this.recipeIngredients;
    }

    public int getEnergyCost(){
        return this.energyCost;
    }

    public static class Type implements RecipeType<AssemblyRecipe>{
        private Type(){}
        public static final Type INSTANCE = new Type();
//        public static final String ID = "assembly";
    }

    public static class Serializer implements RecipeSerializer<AssemblyRecipe>{
        public static final Serializer INSTANCE = new Serializer();
//        public static final ResourceLocation ID = new ResourceLocation(ChonkyReactors.MODID, "assembly");

        @Override
        public @NotNull AssemblyRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
           /* ItemStack output = CraftingHelper.getItemStack(pSerializedRecipe.get("result").getAsJsonObject(), true);

            int energyCost = pSerializedRecipe.get("energy_cost").getAsInt();

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<StackIngredient> inputs = NonNullList.withSize(ingredients.size(), StackIngredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i,  StackIngredient.Serializer.INSTANCE.parse(ingredients.get(i).getAsJsonObject()));
            }

            return new AssemblyRecipe(pRecipeId, output, inputs, energyCost);*/
            return AssemblyRecipe.CODEC.parse(JsonOps.INSTANCE, pSerializedRecipe).getOrThrow(false, LOGGER::error);
        }

        @Override
        public @Nullable AssemblyRecipe fromNetwork(@NotNull ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         /*   NonNullList<StackIngredient> inputs = NonNullList.withSize(pBuffer.readInt(), StackIngredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++){
                inputs.set(i, StackIngredient.Serializer.INSTANCE.parse(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            int energy = pBuffer.readInt();
            return new AssemblyRecipe(pRecipeId, output, inputs, energy);*/
            return pBuffer.readJsonWithCodec(AssemblyRecipe.CODEC);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, AssemblyRecipe pRecipe) {
         /*   pBuffer.writeInt(pRecipe.getStackIngredients().size());

            for (StackIngredient ingredient : pRecipe.getStackIngredients()){
                CraftingHelper.write(pBuffer, ingredient);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(RegistryAccess.EMPTY), false);
            pBuffer.writeInt(pRecipe.getEnergyCost());*/
            pBuffer.writeJsonWithCodec(AssemblyRecipe.CODEC, pRecipe);
        }
    }
}
