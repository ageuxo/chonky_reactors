package io.github.ageuxo.chonkyreactors;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockDefinition;
import io.github.ageuxo.chonkyreactors.multiblock.MultiblockRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.Objects;

public class ModCommands {
    public static LiteralArgumentBuilder<CommandSourceStack> chonkyBaseCommand() {
        return LiteralArgumentBuilder.<CommandSourceStack>literal("chonky").executes(context -> {
            Objects.requireNonNull(context.getSource().getPlayer())
                    .sendSystemMessage(Component.literal("chonks confirmed!"), true);
            return Command.SINGLE_SUCCESS;
        });


    }

    public static ArgumentBuilder<CommandSourceStack, ?> multiblockCommand() {
        return LiteralArgumentBuilder.<CommandSourceStack>literal("multiblocks").executes(ctx->getMultiblockPage(ctx, 1))
                .then(Commands.argument("page", IntegerArgumentType.integer(1))
                        .executes(ctx->getMultiblockPage(ctx, IntegerArgumentType.getInteger(ctx, "page"))));
    }

    public static int getMultiblockPage(CommandContext<CommandSourceStack> context, int page){
        Registry<MultiblockDefinition> registry = context.getSource().registryAccess().registry(MultiblockRegistry.KEY)
                .orElseThrow(()->new CommandRuntimeException(Component.literal("Unable to find Multiblock registry")));
        List<String> cache = MultiblockRegistry.getMultiblockRLs(registry);
        int index = (page-1)*10;
        MutableComponent message = Component.empty();
        if (registry.entrySet().isEmpty()){
           message.append(Component.translatable("command.chonky_reactors.multiblock.empty"));
           context.getSource().sendFailure(message);
           return 0;
        } else if (index < registry.entrySet().size()) {
            List<String> strings = MultiblockRegistry.getMultiblockRLs(registry, index, Math.min(cache.size(), index + 10));
            message.append(Component.literal("||-----------------------------------\n").withStyle(ChatFormatting.RESET, ChatFormatting.BOLD));
            strings.forEach((s)-> message.append(Component.literal("  "+s+"\n").withStyle(ChatFormatting.AQUA)));
            context.getSource().sendSuccess( () -> message.append(
                    Component.translatable("command.chonky_reactors.multiblock.result", page, cache.size() % 10, index+1, Math.min(cache.size(), index + 10))
                    .withStyle(ChatFormatting.RESET, ChatFormatting.BOLD)), false);
            return Command.SINGLE_SUCCESS;
        } else {
            message.append(Component.translatable("command.chonky_reactors.multiblock.invalid_page", page, (int)(((float)cache.size()) % 10)));
            context.getSource().sendFailure(message);
            return 0;
        }
    }

}
