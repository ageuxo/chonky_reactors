package io.github.ageuxo.chonkyreactors.gui.menu;

import io.github.ageuxo.chonkyreactors.block.entity.AssemblyBlockEntity;
import io.github.ageuxo.chonkyreactors.util.DataType;
import io.github.ageuxo.chonkyreactors.util.MachineData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class AssemblyBlockMenu extends AbstractMachineMenu {
    public final AssemblyBlockEntity blockEntity;

    public AssemblyBlockMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new MachineData(4));
            data.add(DataType.PROGRESS, blockEntity::getProgress, blockEntity::setProgress)
                .add(DataType.MAX_PROGRESS, blockEntity::getProgressMax, blockEntity::setProgressMax)
                .add(DataType.ENERGY, blockEntity::getEnergy, null)
                .add(DataType.ENERGY_CAPACITY, blockEntity::getEnergyCapacity, null);
    }

    public AssemblyBlockMenu(int id, Inventory inv, BlockEntity entity, MachineData data) {
        super(ModMenuTypes.ASSEMBLY_BLOCK_MENU.get(), id, inv, entity, data, data.getCount());
        checkContainerSize(inv, 5);
        blockEntity = (AssemblyBlockEntity) entity;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 46, 17));
            this.addSlot(new SlotItemHandler(handler, 1, 66, 17));
            this.addSlot(new SlotItemHandler(handler, 2, 56, 53));
            this.addSlot(new SlotItemHandler(handler, 3, 106, 35));
            this.addSlot(new SlotItemHandler(handler, 4, 126, 35));
        });


    }



}
