package op65n.tech.terrain.bukkit.util;

import op65n.tech.terrain.bukkit.TerrainGenerator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public final class Task {

    private static final TerrainGenerator PLUGIN = JavaPlugin.getPlugin(TerrainGenerator.class);

    public static void async(final Runnable runnable) {
        Bukkit.getScheduler().scheduleAsyncDelayedTask(PLUGIN, runnable);
    }

}
