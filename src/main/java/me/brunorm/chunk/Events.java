package me.brunorm.chunk;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitTask;

public class Events implements Listener {

	HashMap<Chunk, BukkitTask> tasks = new HashMap<Chunk, BukkitTask>();
	HashMap<Chunk, Integer> chunk_seconds = new HashMap<Chunk, Integer>();

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

	void cancel(Chunk c) {
		Events.this.tasks.get(c).cancel();
		Events.this.tasks.remove(c);
	}

	@EventHandler
	void onMove(PlayerMoveEvent e) {
		if (!ChunkFall.enabled)
			return;
		final Player player = e.getPlayer();

		if (player.getGameMode() != GameMode.SURVIVAL //
				&& player.getGameMode() != GameMode.ADVENTURE)
			return;

		final Chunk chunk = player.getLocation().getChunk();
		if (this.tasks.get(chunk) != null)
			return;

		this.tasks.put(chunk, Bukkit.getScheduler().runTaskTimer(ChunkFall.get(), new Runnable() {

			int seconds = ChunkFall.get().getConfig().getInt("seconds_until_fall");

			@Override
			public void run() {

				for (final Entity en : chunk.getEntities()) {
					if (!(en instanceof Player))
						continue;
					final Player p = (Player) en;

					if (this.seconds > 0)
						ChunkFall.get().NMS().sendTitle(p, "&c&l" + this.seconds,
								ChunkFall.get().getConfig().getString("subtitle"));
					else {
						ChunkFall.get().NMS().sendTitle(p, ChunkFall.get().getConfig().getString("title"),
								ChunkFall.get().getConfig().getString("subtitle"));
					}
				}

				if (this.seconds <= 0) {
					Events.this.fallChunk(chunk);
					Events.this.cancel(chunk);
					return;
				}

				this.seconds -= 1;
			}
		}, 0, 20L));
	}
}
