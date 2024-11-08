package net.steampn.primitivechests.common.blocks.advancedprimitivechest;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static net.steampn.primitivechests.Primitivechests.MODID;

public class AdvPrimitiveChestScreen extends AbstractContainerScreen<AdvPrimitiveChestMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(MODID, "textures/gui/advanced_primitive_chest.png");

    public AdvPrimitiveChestScreen(AdvPrimitiveChestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
//        guiGraphics.drawString(this.font, this.playerInventoryTitle.getString(),
//                this.inventoryLabelX,this.inventoryLabelY - 64,4210752,false);
        guiGraphics.drawString(this.font, this.title,
                this.inventoryLabelX,this.inventoryLabelY - 65,4210752,false);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0,0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
