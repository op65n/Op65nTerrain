package op65n.tech.terrain.bukkit;

import op65n.tech.terrain.bukkit.util.ChunkFile;
import op65n.tech.terrain.bukkit.world.WorldGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TerrainGenerator extends JavaPlugin {

    @Override
    public void onDisable() {
        ChunkFile.saveData();
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull final String worldName, @Nullable final String id) {
        return new WorldGenerator();
    }

}
