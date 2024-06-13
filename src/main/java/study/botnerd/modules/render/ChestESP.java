package study.botnerd.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import study.botnerd.modules.Category;
import study.botnerd.modules.Module;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;

public class ChestESP extends Module {

    private final Minecraft mc = Minecraft.getMinecraft();

    public ChestESP() {
        super("ChestESP", "test", Category.Render);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (this.isToggled()) {
            GlStateManager.pushMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableTexture2D();
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
            GlStateManager.disableLighting();
            GlStateManager.disableCull();

            for (TileEntity tileEntity : mc.world.loadedTileEntityList) {
                if (tileEntity instanceof TileEntityChest) {
                    TileEntityChest chest = (TileEntityChest) tileEntity;
                    AxisAlignedBB bb = new AxisAlignedBB(
                            chest.getPos().getX() - mc.getRenderManager().viewerPosX,
                            chest.getPos().getY() - mc.getRenderManager().viewerPosY,
                            chest.getPos().getZ() - mc.getRenderManager().viewerPosZ,
                            chest.getPos().getX() + 1 - mc.getRenderManager().viewerPosX,
                            chest.getPos().getY() + 1 - mc.getRenderManager().viewerPosY,
                            chest.getPos().getZ() + 1 - mc.getRenderManager().viewerPosZ
                    );

                    drawBoundingBox(bb);
                }
            }

            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    private void drawBoundingBox(AxisAlignedBB bb) {
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.minZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.maxX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.maxX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.getBuffer().pos(bb.minX, bb.minY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();
        tessellator.getBuffer().pos(bb.minX, bb.maxY, bb.maxZ).color(0.0F, 1.0F, 0.0F, 1.0F).endVertex();

        tessellator.draw();
    }
}
