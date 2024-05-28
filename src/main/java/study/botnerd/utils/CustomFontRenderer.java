package study.botnerd.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class CustomFontRenderer {
    private FontRenderer fontRenderer;

    public CustomFontRenderer() {
        this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public void drawString(String text, int x, int y, int color) {
        this.fontRenderer.drawString(text, x, y, color);
    }

    public void drawStringWithShadow(String text, int x, int y, int color) {
        this.fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public void drawStringWithEffects(String text, int x, int y, int color, boolean shadow, boolean bold, boolean italic) {
        if (shadow) {
            this.fontRenderer.drawStringWithShadow(text, x, y, color);
        } else {
            this.fontRenderer.drawString(text, x, y, color);
        }
        if (bold) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, 0.0F);
            GL11.glScalef(1.25F, 1.25F, 1.25F);
            GL11.glTranslatef((float) (-x), (float) (-y), 0.0F);
            this.fontRenderer.drawString(text, x, y, color);
            GL11.glPopMatrix();
        }
        if (italic) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x, (float) y, 0.0F);
            GL11.glScalef(0.8F, 0.8F, 0.8F);
            GL11.glTranslatef((float) (-x), (float) (-y), 0.0F);
            this.fontRenderer.drawString(text, x, y, color);
            GL11.glPopMatrix();
        }
    }

    public int getStringWidth(String text) {
        return this.fontRenderer.getStringWidth(text);
    }

    public int getFontHeight() {
        return this.fontRenderer.FONT_HEIGHT;
    }

    public void setCustomFont(ResourceLocation location) {
        this.fontRenderer = new FontRenderer(Minecraft.getMinecraft().gameSettings, location, Minecraft.getMinecraft().renderEngine, false);
    }

    public void drawStringWithSize(String text, int x, int y, int color, float size) {
        GL11.glPushMatrix();
        GL11.glScalef(size, size, size);
        this.fontRenderer.drawString(text, (int) (x / size), (int) (y / size), color);
        GL11.glPopMatrix();
    }

    public void drawStringWithGradient(String text, int x, int y, int startColor, int endColor) {
        int width = this.fontRenderer.getStringWidth(text);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int color = startColor + (endColor - startColor) * i / text.length();
            this.fontRenderer.drawString(String.valueOf(c), x + (int) (width * i / text.length()), y, color);
        }
    }

    public void drawCenteredString(String text, int x, int y, int color) {
        int width = this.fontRenderer.getStringWidth(text);
        this.fontRenderer.drawString(text, x - width / 2, y - this.fontRenderer.FONT_HEIGHT / 2, color);
    }

    public void drawCenteredStringWithShadow(String text, int x, int y, int color) {
        int width = this.fontRenderer.getStringWidth(text);
        this.fontRenderer.drawStringWithShadow(text, x - width / 2, y - this.fontRenderer.FONT_HEIGHT / 2, color);
    }

    public void drawStringCenteredWithShadow(String text, int x, int y, int color) {
        int width = this.fontRenderer.getStringWidth(text);
        int height = this.fontRenderer.FONT_HEIGHT;
        this.fontRenderer.drawStringWithShadow(text, x - width / 2, y - height / 2, color);
    }

    public void drawStringWithTrimmedWidth(String text, int x, int y, int color, int maxWidth) {
        String trimmedText = this.fontRenderer.trimStringToWidth(text, maxWidth);
        this.fontRenderer.drawString(trimmedText, x, y, color);
    }

    public void drawStringCenteredWithTrimmedWidth(String text, int x, int y, int color, int maxWidth) {
        int width = this.fontRenderer.getStringWidth(text);
        this.fontRenderer.drawStringWithShadow(text, x - width / 2, y, color);
    }
}
