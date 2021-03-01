package op65n.tech.terrain.bukkit.util;

import op65n.tech.terrain.bukkit.TerrainGenerator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class ChunkFile {

    private static final TerrainGenerator PLUGIN = JavaPlugin.getPlugin(TerrainGenerator.class);
    private final Set<LocationHolder> holders = new HashSet<>();

    public void assignData(final int x, final int y, final int z) {
        this.holders.add(new LocationHolder(x, y, z));
    }

    public void saveData(final int chunkX, final int chunkZ) {
        final File file = new File(PLUGIN.getDataFolder(), String.format(
                "/chunks/%s-%s.yml", chunkX, chunkZ
        ));
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        for (int z = 0; z < 16; z++) {
            String result = "";

            for (int x = 0; x < 16; x++) {
                final LocationHolder holder = getHolder(x, z);

                result += holder.y + " ";
            }

            configuration.set(String.valueOf(z), result);
        }

        try { configuration.save(file);
        } catch (final IOException ignored) { }
    }

    private LocationHolder getHolder(final int x, final int z) {
        final Optional<LocationHolder> locHol = this.holders.stream()
                .filter(holder -> holder.x == x)
                .filter(holder -> holder.z == z)
                .findFirst();

        return locHol.get();
    }

    private static final class LocationHolder {
        private final int x;
        private final int y;
        private final int z;

        public LocationHolder(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

}
