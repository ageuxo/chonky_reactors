package io.github.ageuxo.chonkyreactors.network;

import com.mojang.logging.LogUtils;
import io.github.ageuxo.chonkyreactors.util.ModUtils;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.Optional;

public class PacketHandler {
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ModUtils.modRL("main"),
            ()-> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings("UnusedAssignment")
    public static void register(){
        int id = 0;
        INSTANCE.registerMessage(id++, ReactorOutlinesS2CPacket.class, ReactorOutlinesS2CPacket::encode, ReactorOutlinesS2CPacket::decode, ReactorOutlinesS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
