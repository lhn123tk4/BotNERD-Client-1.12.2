package study.botnerd;

import study.botnerd.commands.CommandManager;
import study.botnerd.config.SaveLoad;
import study.botnerd.gui.NicknameGui;
import study.botnerd.gui.botnerdGui;
import study.botnerd.gui.clickgui.ClickGuiManager;
import study.botnerd.gui.clickgui.SettingsManager;
import study.botnerd.modules.Module;
import study.botnerd.modules.ModuleManager;
import net.minecraft.client.gui.*;
import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import study.botnerd.proxy.CommonProxy;
import study.botnerd.utils.ChatUtil;
import study.botnerd.utils.ColorUtil;
import study.botnerd.utils.TextureUtil;
import study.botnerd.utils.notifications.Notification;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import java.net.URI;
import java.net.URISyntaxException;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main
{
    public static final String MODID = "bot";
    public static final String NAME = "BotNERD";
    public static final String VERSION = "1.0";
    public static final String AUTHOR = "BotNERD";
    public static final String CLIENT_PROXY_CLASS = "study.botnerd.proxy.ClientProxy";
    public static final String COMMON_PROXY_CLASS = "study.botnerd.proxy.CommonProxy";
    
    public static Main instance;
    public static String LicenseOwner;
    
    public ModuleManager moduleManager;
    public SettingsManager settingsManager;
    public ClickGuiManager clickGui;
    public Notification notificationManager;
    public CommandManager cmdManager;
    public SaveLoad saveLoad;
    
    public Minecraft mc = Minecraft.getMinecraft();
    
    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	try {
            String root = "assets/ref/textures/";
            ClassLoader classLoader = getClass().getClassLoader();

            BufferedImage icon16 = ImageIO.read(classLoader.getResourceAsStream(root + "16x.png"));
            BufferedImage icon32 = ImageIO.read(classLoader.getResourceAsStream(root + "32x.png"));
            BufferedImage icon64 = ImageIO.read(classLoader.getResourceAsStream(root + "64x.png"));
            BufferedImage icon128 = ImageIO.read(classLoader.getResourceAsStream(root + "128x.png"));

            Display.setIcon(new ByteBuffer[]{
                    TextureUtil.convertToByteBuffer(icon16),
                    TextureUtil.convertToByteBuffer(icon32),
                    TextureUtil.convertToByteBuffer(icon64),
                    TextureUtil.convertToByteBuffer(icon128)
            });
    		
    	}catch (Exception e) {
			System.out.println(e);
		}
        Display.setTitle("BotNERD Client - Loading...");
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        saveLoad = new SaveLoad();
        clickGui = new ClickGuiManager();
        notificationManager = new Notification();
        cmdManager = new CommandManager();
        cmdManager.init();
        Display.setTitle("BotNERD Client");
    }
    @SubscribeEvent
    public void key(KeyInputEvent e) {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null)
            return;
        try {
            if (Keyboard.isCreated()) {
                if (Keyboard.getEventKeyState()) {
                    int keyCode = Keyboard.getEventKey();
                    if (keyCode <= 0)
                        return;
                    for (Module m : moduleManager.modules) {
                        if (m.getKey() == keyCode && keyCode > 0) {
                            m.toggle();
                        }
                    }
                }
            }
        } catch (Exception q) { q.printStackTrace(); }
    }
    @SubscribeEvent
    public void onGuiScreen(GuiScreenEvent event) {
    	if(event.getGui() instanceof GuiMainMenu) {
    		ScaledResolution sr = new ScaledResolution(mc);
    		Gui.drawRect(sr.getScaledWidth()-mc.fontRenderer.getStringWidth("§fLicense: §7" + LicenseOwner)-3, 0, sr.getScaledWidth()-mc.fontRenderer.getStringWidth("§fLicense: §7" + LicenseOwner)+mc.fontRenderer.getStringWidth("§fLicense: §7" + LicenseOwner), mc.fontRenderer.FONT_HEIGHT+2, 0x8F000000);
    		Gui.drawRect(0, 0, mc.fontRenderer.getStringWidth(NAME+" Client§f v" + VERSION)+2, mc.fontRenderer.FONT_HEIGHT+2, 0x8F000000);
    		Gui.drawRect(0, mc.fontRenderer.FONT_HEIGHT+2, mc.fontRenderer.getStringWidth("§fCreated by: §7BotNERD TEAM")+2, mc.fontRenderer.FONT_HEIGHT+mc.fontRenderer.FONT_HEIGHT+2, 0x8F000000);
    		mc.fontRenderer.drawStringWithShadow(NAME+" Client§f v" + VERSION, 2, 2, ColorUtil.rainbow(300));
    		mc.fontRenderer.drawStringWithShadow("§fCreated by: §oBotNERD TEAM", 2, mc.fontRenderer.FONT_HEIGHT+2, -1);
    		mc.fontRenderer.drawStringWithShadow("§fLicense: §o" + LicenseOwner, sr.getScaledWidth()-mc.fontRenderer.getStringWidth("§fLicense: §o" + LicenseOwner)-1, 2, -1);
    	}
    }
    
    @SubscribeEvent
    public void onGuiScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
		ScaledResolution sr = new ScaledResolution(mc);
    	if(event.getGui() instanceof GuiMultiplayer) {
    		event.getButtonList().add(new GuiButton(100, 5, 5, 75, 20, "Nickname"));
    	}
    	if(event.getGui() instanceof GuiMainMenu) {
    		event.getButtonList().add(new GuiButton(101, sr.getScaledWidth() / 2 + 104,  (sr.getScaledHeight() / 4 + 48 ) + 84, 20, 20, "RC"));
    	}
    }
    
    @SubscribeEvent
    public void onClickButton(ActionPerformedEvent event) {
    	if(event.getGui() instanceof GuiMultiplayer) {
            if (event.getButton().id == 100)
            {
                mc.displayGuiScreen(new NicknameGui());
            }
    	}
    	if(event.getGui() instanceof GuiMainMenu) {
            if (event.getButton().id == 101)
            {
            	mc.displayGuiScreen(new botnerdGui());
            	/*
            	 try {
            		Desktop d = Desktop.getDesktop();
					d.browse(new URI("https://discord.com/invite/2emmzdydHB"));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}*/
            }
        }
    }
    public void sendPacket(Packet packet) {
        Minecraft.getMinecraft().player.connection.sendPacket(packet);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(CommandManager.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                CommandManager.callCommand(event.getMessage().substring(1));
            } catch (Exception e) {
                e.printStackTrace();
                ChatUtil.sendErrorChatMessage("Error: " + e.getMessage());
            }
        }
    }
}
