package net.nuclearteam.createnuclear.content.multiblock.input.item;

import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.nuclearteam.createnuclear.foundation.gui.CNGuiTextures;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class ReactorRodInputScreen extends AbstractSimiContainerScreen<ReactorRodInputMenu> {

    protected static final CNGuiTextures background = CNGuiTextures.REACTOR_SLOT_INVENTOR;

    public ReactorRodInputScreen(ReactorRodInputMenu container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void init() {
        setWindowSize(background.width, background.height+ 4 + AllGuiTextures.PLAYER_INVENTORY.getHeight());
        setWindowOffset(0,0);
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int invX = getLeftOfCentered(PLAYER_INVENTORY.getWidth());
        int invY = topPos + background.height + 4;
        renderPlayerInventory(guiGraphics, invX, invY);

        int x = leftPos;
        int y = topPos;

        background.render(guiGraphics, x, y);

    }

    // The player inventory is wider than the background, so its outer columns fall outside
    // the window bounds and vanilla would treat clicks there as "outside" (THROW instead of QUICK_MOVE).
    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        int invX = getLeftOfCentered(PLAYER_INVENTORY.getWidth());
        int invY = topPos + background.height + 4;
        if (mouseX >= invX && mouseX < invX + PLAYER_INVENTORY.getWidth()
                && mouseY >= invY && mouseY < invY + PLAYER_INVENTORY.getHeight())
            return false;
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }
}
