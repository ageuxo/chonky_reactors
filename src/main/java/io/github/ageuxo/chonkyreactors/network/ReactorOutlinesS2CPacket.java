package io.github.ageuxo.chonkyreactors.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public record ReactorOutlinesS2CPacket(List<AABB> shapes, BlockPos blockPos) {
    public static final Codec<ReactorOutlinesS2CPacket> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ModUtils.AABB_CODEC.listOf().fieldOf("shapes").forGetter(ReactorOutlinesS2CPacket::shapes),
            BlockPos.CODEC.fieldOf("pos").forGetter(ReactorOutlinesS2CPacket::blockPos)
    ).apply(instance, ReactorOutlinesS2CPacket::new));

    public static void handle(ReactorOutlinesS2CPacket msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.handleReactorOutlinePacket(msg)));
        context.setPacketHandled(true);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(CODEC, this);
    }

    public static ReactorOutlinesS2CPacket decode(FriendlyByteBuf buf) {
        return buf.readJsonWithCodec(CODEC);
    }


}
