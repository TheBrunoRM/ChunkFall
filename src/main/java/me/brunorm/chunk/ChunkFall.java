package me.brunorm.chunk;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkFall extends JavaPlugin {

	static ChunkFall plugin;

	@Override
	public void onEnable() {
		ChunkFall.plugin = this;
		Bukkit.getServer().getConsoleSender().sendMessage("Hello! ChunkFall enabled.");
		Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
		this.getCommand("chunkfall").setExecutor(new MainCommand());
		if (!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();
	}

	public static ChunkFall get() {
		return plugin;
	}
}
