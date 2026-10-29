package net.nuclearteam.createnuclear.content.multiblock.bluePrintItem;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.nuclearteam.createnuclear.CNPackets;
import net.nuclearteam.createnuclear.foundation.gui.CNGuiTextures;

import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

@ParametersAreNonnullByDefault
@SuppressWarnings({"unused"})
public class ReactorBluePrintItemScreen extends AbstractSimiContainerScreen<ReactorBluePrintMenu> {
    protected static final CNGuiTextures BG = CNGuiTextures.CONFIGURED_PATTERN_GUI;

    public ReactorBluePrintItemScreen(ReactorBluePrintMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    protected void init() {
        setWindowSize(BG.width, BG.height + PLAYER_INVENTORY.getHeight());
        setWindowOffset(0, 0);
        super.init();
        clearWidgets();
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos+38;

        BG.render(guiGraphics, x+23, y-19);
        renderPlayerInventory(guiGraphics, x+23, y+175);

        guiGraphics.drawString(font, title, x+26, y-12, 0x592424, false); // renders the screen title
    }

    // Hold left click and drag over the pattern grid to place (or erase, with an empty hand) several rods at once.
    private final Set<Slot> paintedSlots = new HashSet<>();
    private boolean painting = false;

    private static boolean isPatternSlot(@Nullable Slot slot) {
        return slot != null && slot.index >= 36;
    }

    @Nullable
    private Slot getPatternSlotAt(double mouseX, double mouseY) {
        double x = mouseX - leftPos;
        double y = mouseY - topPos;
        for (Slot slot : menu.slots) {
            if (isPatternSlot(slot) && x >= slot.x - 1 && x < slot.x + 17 && y >= slot.y - 1 && y < slot.y + 17)
                return slot;
        }
        return null;
    }

    private void paint(@Nullable Slot slot) {
        if (isPatternSlot(slot) && paintedSlots.add(slot))
            slotClicked(slot, slot.index, 0, ClickType.PICKUP);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Slot slot = getPatternSlotAt(mouseX, mouseY);
        if (button == 0 && isPatternSlot(slot)) {
            painting = true;
            paintedSlots.clear();
            // An empty hand clears the slot immediately in super, so don't clear it a second time
            if (menu.getCarried().isEmpty())
                paintedSlots.add(slot);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (painting && button == 0) {
            paint(getPatternSlotAt(mouseX, mouseY));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (painting && button == 0) {
            painting = false;
            paint(getPatternSlotAt(mouseX, mouseY));
            paintedSlots.clear();
            // With an empty hand, vanilla only consumes its pending "skip next release" flag here
            if (menu.getCarried().isEmpty())
                return super.mouseReleased(mouseX, mouseY, button);
            // Otherwise cancel the vanilla drag state so releasing doesn't also click the slot under the cursor
            isQuickCrafting = false;
            quickCraftSlots.clear();
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (!ItemStack.matches(menu.player.getMainHandItem(), menu.contentHolder)) {
            menu.player.closeContainer();
        }

        CompoundTag tag = menu.contentHolder.getOrCreateTag();
        if (tag.contains("pattern")) {
            sendValueUpdate(tag, tag.getFloat("heat"),
                    tag.getInt("coolerTime"),
                    tag.getInt("fuelTime"),
                    tag.getInt("countCoolerRod"),
                    tag.getInt("countFuelRod")
            );
        }
    }


    private static void sendValueUpdate(CompoundTag tag, float heat, int coolerTime, int fuelTime, int countCoolerRod, int countFuelRod) {
        CNPackets.getChannel().sendToServer(new ReactorBluePrintItemPacket(tag, heat, coolerTime, fuelTime, countCoolerRod, countFuelRod));
    }
}
