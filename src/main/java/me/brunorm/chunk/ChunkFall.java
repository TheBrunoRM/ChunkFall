package me.brunorm.chunk;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.brunorm.bukkitutils.BukkitReflection;

public class ChunkFall extends JavaPlugin {

	static ChunkFall plugin;
	public static boolean enabled = false;
	private String packageName;
	private String serverPackageVersion;
	private BukkitReflection nms;

	@Override
	public void onEnable() {
		ChunkFall.plugin = this;
		this.packageName = this.getServer().getClass().getPackage().getName();
		this.serverPackageVersion = this.packageName.substring(this.packageName.lastIndexOf('.') + 1);
		this.nms = new BukkitReflection();

		Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
		this.getCommand("chunkfall").setExecutor(new MainCommand());
		Bukkit.getLogger().info("Hello! ChunkFall enabled.");
		if (!new File(this.getDataFolder(), "config.yml").exists())
			this.saveDefaultConfig();
	}

	public static ChunkFall get() {
		return plugin;
	}

	public BukkitReflection NMS() {
		return this.nms;
	}

	public String getServerPackageVersion() {
		return this.serverPackageVersion;
	}
}
