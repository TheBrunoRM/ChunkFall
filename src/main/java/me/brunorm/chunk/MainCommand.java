package me.brunorm.chunk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MainCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if (args.length <= 0)
			sender.sendMessage("use /" + cmd + " reload");
		else if (args[0].equalsIgnoreCase("reload")) {
			ChunkFall.get().reloadConfig();
			sender.sendMessage("done");
		}
		return false;
	}

}
