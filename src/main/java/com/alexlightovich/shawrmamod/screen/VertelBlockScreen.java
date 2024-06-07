package com.alexlightovich.shawrmamod.screen;

import com.alexlightovich.shawrmamod.ShawrmaMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;

public class VertelBlockScreen extends AbstractContainerScreen<VertelBlockMenu> {

    //private static final ResourceLocation TEXTURE = new ResourceLocation(ShawrmaMod.MODID,"textures/gui/vertel_block_gui.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft","textures/gui/container/furnace.png");
    private static final ResourceLocation LIT_PROGRESS = new ResourceLocation(ShawrmaMod.MODID,"textures/gui/vertel_block/vertel_lit_progress.png");
    private static final ResourceLocation BURN_PROGRESS = new ResourceLocation(ShawrmaMod.MODID,"textures/gui/vertel_block/vertel_burn_progress.png");

    public VertelBlockScreen(VertelBlockMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(BURN_PROGRESS, x + 79, y + 34, 0, 0, menu.getScaledProgress(), 16,24,16);
        }
        if(menu.isLiting()) {
            guiGraphics.blit(LIT_PROGRESS, x + 57+14, y + 37+14, 14, 14, -14, -menu.getLitScaledProgress(),14,14);

        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics, mouseX,mouseY,delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
