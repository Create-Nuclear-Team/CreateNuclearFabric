package net.nuclearteam.createnuclear.content.multiblock.input;

import com.simibubi.create.foundation.gui.menu.MenuBase;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotItemHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.nuclearteam.createnuclear.CNMenus;
import net.nuclearteam.createnuclear.CreateNuclear;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ReactorInputMenu extends MenuBase<ReactorInputEntity> {
    public ReactorInputMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public ReactorInputMenu(MenuType<?> type, int id, Inventory inv, ReactorInputEntity contentHolder) {
        super(type, id, inv, contentHolder);
    }

    public static ReactorInputMenu create(int id, Inventory inv, ReactorInputEntity contentHolder) {
        return new ReactorInputMenu(CNMenus.SLOT_ITEM_STORAGE.get(), id, inv, contentHolder);
    }

    @Override
    protected ReactorInputEntity createOnClient(FriendlyByteBuf extraData) {
        ClientLevel world = Minecraft.getInstance().level;
        BlockEntity entity = world.getBlockEntity(extraData.readBlockPos());
        if (entity instanceof ReactorInputEntity reactorInputEntity) {
            reactorInputEntity.readClient(extraData.readNbt());
            return reactorInputEntity;
        }
        return null;
    }

    @Override
    protected void initAndReadInventory(ReactorInputEntity contentHolder) {
    }

    @Override
    protected void addSlots() {
        addSlot(new SlotItemHandler(contentHolder.inventory, 0, 24, 29));
        addSlot(new SlotItemHandler(contentHolder.inventory, 1, 57, 29));

        addPlayerSlots(-31, 97);
    }

    @Override
    protected void saveData(ReactorInputEntity contentHolder) {
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot clickedSlot = getSlot(index);
        if (!clickedSlot.hasItem())
            return ItemStack.EMPTY;
        ItemStack stack = clickedSlot.getItem();
        if (index < 2)
            moveItemStackTo(stack, 2, slots.size(), false);
        else {
            if (moveItemStackTo(stack, 0, 1, false) || moveItemStackTo(stack, 2, 3, false)) {
                CreateNuclear.LOGGER.warn("Slot moved to empty stack, {}, {}, {}", stack, clickedSlot, index);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        CreateNuclear.LOGGER.warn("Slot clicked {}, {}, {}", slotId, button, clickType);
        super.clicked(slotId, button, clickType, player);
    }


//    @Override
//    public void clicked(int slotId, int button, ClickType clickType, Player player) {
//        if (clickType == ClickType.THROW) {
//            int[] targetSlotIds = {9, 18, 27, 0, 1, 28, 19, 10, 16, 17, 26, 25, 34, 35, 8, 7};
//            for (int id : targetSlotIds) {
//                if (slotId == id) {
//                    clickType = ClickType.PICKUP;
//                    super.clicked(slotId, button, clickType, player);
//                }
//            }
//            return;
//        }
//        super.clicked(slotId, button, clickType, player);
//    }
}
