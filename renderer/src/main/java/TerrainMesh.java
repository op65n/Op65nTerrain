import op65n.tech.terrain.common.noise.impl.CubicNoise;
import op65n.tech.terrain.common.util.Math;

import java.util.*;

public final class TerrainMesh {

    private static final CubicNoise TERRAIN_GENERATOR = new CubicNoise(130102, 7);
    private final Set<TerrainBlock> terrainMesh = new HashSet<>();

    private final int x;
    private final int z;

    public TerrainMesh(final int x, final int z) {
        this.x = x;
        this.z = z;
    }

    public Set<TerrainBlock> populate(final int renderDistance) {
        final int minX = x - renderDistance;
        final int maxX = x + renderDistance;

        final int minZ = z - renderDistance;
        final int maxZ = z + renderDistance;

        for (int coordinateX = minX; coordinateX <= maxX; coordinateX++) {
            for (int coordinateZ = minZ; coordinateZ <= maxZ; coordinateZ++) {
                final double heightValue = TERRAIN_GENERATOR.sample(coordinateX, coordinateZ);

                final TerrainBlock block = new TerrainBlock(coordinateX, coordinateZ, heightValue);
                terrainMesh.add(block);
            }
        }

        final Set<TerrainBlock> finished = new HashSet<>();

        for (final TerrainBlock block : terrainMesh) {
            final Map<BlockDirection, TerrainBlock> mappings = getDirectionMappings(block);

            block.directionMapping.putAll(mappings);
            finished.add(block);
        }

        return finished;
    }

    private Map<BlockDirection, TerrainBlock> getDirectionMappings(final TerrainBlock block) {
        final Map<BlockDirection, TerrainBlock> result = new HashMap<>();

        final TerrainBlock west = getBlockAtPosition(block.x - 1, block.z, block.getMappedY());
        final TerrainBlock south = getBlockAtPosition(block.x, block.z - 1, block.getMappedY());
        final TerrainBlock east = getBlockAtPosition(block.x + 1, block.z, block.getMappedY());
        final TerrainBlock north = getBlockAtPosition(block.x, block.z + 1, block.getMappedY());

        result.put(BlockDirection.WEST, west);
        result.put(BlockDirection.SOUTH, south);
        result.put(BlockDirection.EAST, east);
        result.put(BlockDirection.NORTH, north);

        return result;
    }

    private TerrainBlock getBlockAtPosition(final int x, final int z, final int y) {
        return terrainMesh.stream()
                .filter(it -> it.x == x && it.z == z && it.getMappedY() == y)
                .findAny().orElse(null);
    }

    public static final class TerrainBlock {

        private final int x;
        private final int z;

        private final double valueY;
        private final Map<BlockDirection, TerrainBlock> directionMapping = BlockDirection.getDefaultMapping();

        public TerrainBlock(final int x, final int z, final double valueY) {
            this.x = x;
            this.z = z;
            this.valueY = valueY;
        }

        public int getMappedY() {
            return (int) Math.map(this.valueY, -1, 1, 0, 30);
        }

        public int getX() {
            return x;
        }

        public int getZ() {
            return z;
        }

        public double getValueY() {
            return valueY;
        }

        public Map<BlockDirection, TerrainBlock> getDirectionMapping() {
            return directionMapping;
        }
    }

    public enum BlockDirection {
        NORTH(0, 0, 1),
        EAST(1, 0, 0),
        SOUTH(0, 0, -1),
        WEST(-1, 0, 0),
        UP(0, 1, 0),
        DOWN(0, -1, 0);

        private final int x;
        private final int y;
        private final int z;

        BlockDirection(final int x, final int y, final int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getZ() {
            return z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        private static Map<BlockDirection, TerrainBlock> getDefaultMapping() {
            final Map<BlockDirection, TerrainBlock> mapping = new HashMap<>();

            Arrays.stream(values()).forEach(it -> mapping.put(it, null));

            return mapping;
        }

    }

}
