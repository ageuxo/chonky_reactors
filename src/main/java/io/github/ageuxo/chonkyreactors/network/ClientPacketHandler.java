package io.github.ageuxo.chonkyreactors.network;

import io.github.ageuxo.chonkyreactors.block.entity.ReactorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;

public class ClientPacketHandler {

    @SuppressWarnings("DataFlowIssue")
    public static void handleReactorOutlinePacket(ReactorOutlinesS2CPacket packet){
        BlockPos pos = packet.blockPos();

        ClientLevel level = Minecraft.getInstance().level;
        if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof ReactorBlockEntity reactorEntity){
            reactorEntity.setPartBoxes(packet.shapes());
        }
    }
}
