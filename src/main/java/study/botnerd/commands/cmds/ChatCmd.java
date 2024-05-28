package study.botnerd.commands.cmds;

import net.minecraftforge.fml.client.FMLClientHandler;
import study.botnerd.commands.Command;
import study.botnerd.utils.ChatUtil;

@Command.Declaration(name = "Chat", syntax = "chat clear", alias = {"chat"})
public class ChatCmd extends Command{
	
	@Override
	public void onCommand(String command, String[] args) {
		if(args[0].equalsIgnoreCase("clear")){
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().clearChatMessages(true);
		}
		else {
			sendSyntaxError(this);
		}
	}
 
}
