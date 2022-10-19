package me.brunorm.chunk;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

public class Events implements Listener {

	HashMap<Player, Chunk> chunks = new HashMap<Player, Chunk>();
	HashMap<Player, Integer> seconds = new HashMap<Player, Integer>();
	HashMap<Player, BukkitTask> tasks = new HashMap<Player, BukkitTask>();

	void cancel(Player p) {
		// Events.this.chunks.remove(p);
		Events.this.seconds.remove(p);
		final BukkitTask task = Events.this.tasks.get(p);
		if (task != null)
			task.cancel();
		Events.this.tasks.remove(p);
	}

	@SuppressWarnings("deprecation")
	private void fallChunk(Chunk chunk) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				boolean yes = false;
				for (int y = 255; y >= 0; y--) {
					final Block block = chunk.getBlock(x, y, z);
					if (block.getTypeId() == 0)
						continue;
					if (yes) {
						block.setTypeId(0, false);
					}
					yes = true;
					block.getWorld().spawnFallingBlock(block.getLocation(), //
							block.getType(), block.getData());
					block.setTypeId(0, false);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	void title(Player player, String title, String subtitle) {
		player.sendTitle(ChatColor.translateAlternateColorCodes('&', title),
				ChatColor.translateAlternateColorCodes('&', subtitle));
	}

	@EventHandler
	void onMove(PlayerMoveEvent e) {
		final Player player = e.getPlayer();

		/*
		 * final BukkitTask curTask = this.tasks.get(player); if (curTask != null)
		 * return;
		 */

		final Chunk chunk = player.getLocation().getChunk();
		if (chunk != this.chunks.get(player)) {
			this.cancel(player);
			this.chunks.put(player, chunk);
			this.tasks.put(player, Bukkit.getScheduler().runTaskTimer(ChunkFall.get(), new Runnable() {

				@Override
				public void run() {
					final Chunk chunk = player.getLocation().getChunk();
					if (chunk != Events.this.chunks.get(player))
						Events.this.cancel(player);

					int cur;
					if (Events.this.seconds.get(player) == null)
						cur = ChunkFall.get().getConfig().getInt("seconds_until_fall");
					else
						cur = Events.this.seconds.get(player);

					if (cur > 0)
						Events.this.title(player, "&c&l" + cur, ChunkFall.get().getConfig().getString("subtitle"));
					else {
						Events.this.title(player, ChunkFall.get().getConfig().getString("title"),
								ChunkFall.get().getConfig().getString("subtitle"));
						Events.this.fallChunk(chunk);
						Events.this.cancel(player);
						return;
					}
					final int sec = cur - 1;
					Events.this.seconds.put(player, sec);
				}
			}, 0, 20L));
		}
	}
}
