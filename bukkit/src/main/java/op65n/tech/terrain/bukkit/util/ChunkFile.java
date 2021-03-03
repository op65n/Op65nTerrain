package op65n.tech.terrain.bukkit.util;

import op65n.tech.terrain.bukkit.TerrainGenerator;
import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Warning(reason = "Debug use only.")
public final class ChunkFile {

    private static final TerrainGenerator PLUGIN = JavaPlugin.getPlugin(TerrainGenerator.class);
    private static final Set<ChunkHolder> MAP = new HashSet<>();

    public static void assignData(final int chunkX, final int chunkZ, final ChunkGenerator.ChunkData data) {
        final ChunkHolder holder = getHolder(chunkX, chunkZ);

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                holder.addHolder(chunkX >> 4 + x, getBlockHeight(data, x, z), chunkZ >> 4 + z);
            }
        }

        MAP.add(holder);
    }

    public static void saveData() {
        final File file = new File(PLUGIN.getDataFolder(), "map.yml");
        final FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        final Map<Integer, String> mapVisualization = new HashMap<>();
        for (final ChunkHolder holder : getSortedMap()) {
            for (final LocationHolder locationHolder : holder.holders) {
                String value = mapVisualization.getOrDefault(locationHolder.z, "");

                if (value.length() < locationHolder.x) {
                    value += locationHolder.y;
                } else {
                    final String before = value.substring(0, locationHolder.x);
                    final String after = value.substring(locationHolder.x);

                    value = before + locationHolder.y + after;
                }

                mapVisualization.put(locationHolder.z, value);
            }
        }

        mapVisualization.forEach((position, result) -> configuration.set(String.valueOf(position), result));

        try { configuration.save(file);
        } catch (final IOException ignored) { }
    }

    /*
     * z x x x x x
     * z x x x x x
     */
    private static List<ChunkHolder> getSortedMap() {
        return new ArrayList<>(MAP);
    }

    private static int getBlockHeight(final ChunkGenerator.ChunkData data, final int x, final int z) {
        int result = 0;

        for (int y = data.getMaxHeight(); y > 0; y--) {
            if (data.getType(x, y, z) != Material.AIR)
                continue;

            result = y;
            break;
        }

        return result;
    }

    private static ChunkHolder getHolder(final int x, final int z) {
        final Optional<ChunkHolder> chunkHolder = MAP.stream()
                .filter(holder -> holder.x == x)
                .filter(holder -> holder.z == z)
                .findFirst();

        if (chunkHolder.isEmpty())
            return new ChunkHolder(x, z);

        return chunkHolder.get();
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

    private static final class ChunkHolder {
        private final int x;
        private final int z;
        private final Set<LocationHolder> holders = new HashSet<>();

        public ChunkHolder(final int x, final int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() { return this.x; }

        public int getZ() { return this.z; }

        public void addHolder(final int x, final int y, final int z) {
            this.holders.add(new LocationHolder(x, y, z));
        }
    }

}
