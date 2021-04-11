import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;
import op65n.tech.terrain.common.terrain.noise.NoiseCalculation;

import java.util.HashMap;
import java.util.Map;

public final class TestPerlin {

    private static final int MAX_HEIGHT = 255;
    private static final int SEED = 124135235;

    private static final int CHUNK_SIZE = 1;

    private static final TerrainAdjustment ELEVATION = new TerrainAdjustment.AdjustmentBuilder()
            .scale(1 / 800D).build();
    private static final TerrainAdjustment DETAIL = new TerrainAdjustment.AdjustmentBuilder()
            .pow(1, 1).scale(1 / 50D).build();
    private static final TerrainAdjustment ROUGH = new TerrainAdjustment.AdjustmentBuilder()
            .pow(2, 2).additionType(TerrainAdjustment.AdditionType.MULTIPLY)
            .octaves(1).scale(1 / 200D).build();

    private static void test() {
        for (int z = 0; z < 16; z++) {
            String result = "";

            for (int x = 0; x < 16; x++) {
                final int coordinateX = x;
                final int coordinateZ = z;

                final NoiseCalculation calculation = new NoiseCalculation(
                        coordinateX, coordinateZ, MAX_HEIGHT, SEED, NoiseType.IMPROVED,
                        ELEVATION, ROUGH, DETAIL
                );

                final int height = calculation.getHeight();
                result = result.concat(" " + height + " ");
            }

            System.out.println(result);
        }
    }

    private static void testChunks() {
        final Map<Integer, Map<Integer, Map<Integer, String>>> terrain = new HashMap<>();

        for (int chunkZ = -CHUNK_SIZE; chunkZ <= CHUNK_SIZE; chunkZ++) {
            final Map<Integer, Map<Integer, String>> chunkLine = new HashMap<>();

            for (int chunkX = -CHUNK_SIZE; chunkX <= CHUNK_SIZE; chunkX++) {
                final Map<Integer, String> chunkValues = new HashMap<>();

                for (int z = 0; z < 16; z++) {
                    String result = "";

                    for (int x = 0; x < 16; x++) {
                        final int coordinateX = (int) Math.floor(chunkX * 16) + x;
                        final int coordinateZ = (int) Math.floor(chunkZ * 16) + z;

                        final NoiseCalculation calculation = new NoiseCalculation(
                                coordinateX, coordinateZ, MAX_HEIGHT, SEED, NoiseType.IMPROVED,
                                ELEVATION, ROUGH, DETAIL
                        );

                        final int height = calculation.getHeight();
                        result = result.concat(" " + height + " ");
                    }

                    chunkValues.put(z, result);
                }

                chunkLine.put(chunkX, chunkValues);
            }

            terrain.put(chunkZ, chunkLine);
        }

        terrain.forEach((chunkZ, values) -> {
            for (int z = 0; z < 16; z++) {
                String result = "";

                int iteration = values.size() - 1;
                for (final int chunkX : values.keySet()) {
                    final Map<Integer, String> chunkMap = values.get(chunkX);

                    if (--iteration <= 0) result = result.concat(chunkMap.get(z) + "  ");
                    else result = result.concat(chunkMap.get(z) + " ");
                }

                System.out.println(result);
            }

            System.out.println();
        });
    }

    public static void main(final String[] args) {
        test();

    }

}
