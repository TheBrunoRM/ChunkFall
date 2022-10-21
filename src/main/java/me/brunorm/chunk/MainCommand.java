package me.brunorm.chunk;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.brunorm.bukkitutils.Messager;

public class MainCommand implements CommandExecutor {

	String getStatus() {
		if (ChunkFall.enabled)
			return Messager.color("&a&lENABLED");
		else
			return Messager.color("&c&lDISABLED");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if (args.length <= 0 || args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Messager.color("&b&lChunkFall"));
			sender.sendMessage(Messager.color("&eThe plugin is currently: " + this.getStatus()));
			sender.sendMessage(Messager.color("&b/" + cmd + " &aon &e- starts making chunks fall"));
			sender.sendMessage(Messager.color("&b/" + cmd + " &coff &e- stops making chunks fall"));
			sender.sendMessage(Messager.color("&b/" + cmd + " &6reload &e- reloads the plugin configuration"));
		} else if (args[0].equalsIgnoreCase("reload")) {
			ChunkFall.get().reloadConfig();
			sender.sendMessage(Messager.color("&adone"));
		} else if (args[0].equalsIgnoreCase("on")) {
			ChunkFall.enabled = true;
			sender.sendMessage(Messager.color("&aChunks will now start to fall!"));
		} else if (args[0].equalsIgnoreCase("off")) {
			ChunkFall.enabled = false;
			sender.sendMessage(Messager.color("&cChunks will now cease to fall."));
		} else {
			sender.sendMessage("&b???");
		}
		return false;
	}
}
