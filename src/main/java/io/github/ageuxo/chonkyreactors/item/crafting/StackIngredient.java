package io.github.ageuxo.chonkyreactors.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


public class StackIngredient extends AbstractIngredient implements Predicate<ItemStack> {
    public static final StackIngredient EMPTY = new StackIngredient(ItemStack.EMPTY);
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Codec<Either<TagKey<Item>, List<ItemStack>>> VALUE_CODEC = Codec.either(TagKey.hashedCodec(ForgeRegistries.Keys.ITEMS), ItemStack.CODEC.listOf());
    public static final Codec<StackIngredient> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    VALUE_CODEC.fieldOf("values").forGetter(StackIngredient::getValues),
                    Codec.INT.fieldOf("count").forGetter(StackIngredient::getCount)
            ).apply(instance, StackIngredient::new));

    private final TagKey<Item> tagKey;
    private final ItemStack[] itemStacks;
    private final int count;

    public StackIngredient(ItemStack itemStack) {
        this(List.of(itemStack), itemStack.getCount());
    }

    public StackIngredient(Either<TagKey<Item>, List<ItemStack>> either, int count) throws IllegalArgumentException {
        this.count = count;
        if (either.left().isPresent()){
            this.tagKey = either.left().get();
            this.itemStacks = null;
        } else{
            this.tagKey = null;
            this.itemStacks = either.right().orElseThrow(IllegalArgumentException::new).toArray(getItemList().toArray(new ItemStack[0]));
        }
    }

    public StackIngredient(List<ItemStack> itemStacks, int count) {
        this.itemStacks = itemStacks.toArray(new ItemStack[0]);
        this.tagKey = null;
        this.count = count;
    }

    public StackIngredient(TagKey<Item> itemTagKey, int count){
        this.itemStacks = null;
        this.tagKey = itemTagKey;
        this.count = count;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public @NotNull IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow(false, LOGGER::error).getAsJsonObject();

    }

    @Override
    public @NotNull ItemStack[] getItems(){
        return Objects.requireNonNullElseGet(this.itemStacks, () -> ForgeRegistries.ITEMS.tags().getTag(this.tagKey).stream().map(ItemStack::new).toArray(ItemStack[]::new));
    }

    public @NotNull Either<TagKey<Item>, List<ItemStack>> getValues(){
        if (this.tagKey != null){
            return Either.left(this.tagKey);
        } else {
            return Either.right(Arrays.stream(this.itemStacks).toList());
        }
    }

    public List<ItemStack> getItemList(){
        if (this.tagKey != null){
            List<ItemStack> stackList = new ArrayList<>();
            ForgeRegistries.ITEMS.tags().getTag(this.tagKey).stream().forEach(item -> stackList.add(new ItemStack(item)));
            return stackList;
        } else {
            return Arrays.stream(this.itemStacks).toList();
        }

    }

    public int getCount(){
        return this.count;
    }

    //TODO remove
    public boolean containsItem(ItemStack stack){
        return getItemList().stream().anyMatch(stack1 -> stack1.is(stack.getItem()));
    }

    @Override
    public boolean isEmpty() {
        return this.getItemList().isEmpty();
    }

    public static class Serializer implements IIngredientSerializer<StackIngredient>{
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull StackIngredient parse(FriendlyByteBuf buffer) {
            return buffer.readJsonWithCodec(CODEC);
        }

        @Override
        public @NotNull StackIngredient parse(@NotNull JsonObject json) {
            return CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, LOGGER::error);
        }

        @Override
        public void write(FriendlyByteBuf buffer, @NotNull StackIngredient ingredient) {
//            buffer.writeItemStack(ingredient.stack, false);
            buffer.writeJsonWithCodec(CODEC, ingredient);
        }
    }

}
