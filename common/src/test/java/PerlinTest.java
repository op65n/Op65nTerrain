import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.perlin.type.impl.ImprovedPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.OriginalPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.SimplePerlinNoiseGenerator;

import java.text.DecimalFormat;

public final class PerlinTest {

    private static final SimplePerlinNoiseGenerator SIMPLE = new SimplePerlinNoiseGenerator(69);
    private static final ImprovedPerlinNoiseGenerator IMPROVED = new ImprovedPerlinNoiseGenerator(69L);

    private static final int OCTAVES = 8;
    private static final double FREQUENCY = 1.5;
    private static final double AMPLITUDE = 1.2;

    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");
    private static final int CHUNK_SIZE = 2;

    public static void main(final String[] input) {
        //testPerlin(NoiseType.ORIGINAL);
        perlinTest(NoiseType.SIMPLE);
    }

    private static double getNoiseFor(final int x, final int z, final NoiseType type) {
        return switch (type) {
            case SIMPLE -> SIMPLE.noise(x, z, OCTAVES, FREQUENCY, AMPLITUDE);
            case IMPROVED -> IMPROVED.noise(x, z, OCTAVES, FREQUENCY, AMPLITUDE);
            case ORIGINAL -> OriginalPerlinNoiseGenerator.noise(x, z);
        };
    }

    private static void perlinTest(final NoiseType type) {
        for (int z = 0; z < 16; z++) {
            String result = "";

            for (int x = 0; x < 16; x++) {
                result = result.concat(" " + FORMAT.format(getNoiseFor(x, z, type)) + " ");
            }

            System.out.println(result);
        }
    }

    private static void testPerlin(final NoiseType type) {
        for (int chunkZ = -CHUNK_SIZE; chunkZ <= CHUNK_SIZE; chunkZ++) {
            for (int chunkX = -CHUNK_SIZE; chunkX <= CHUNK_SIZE; chunkX++) {
                final int coordinateX = chunkX >> 4;
                final int coordinateZ = chunkZ >> 4;

                String result = "";
                for (int cubeX = 0; cubeX < 16; cubeX++) {
                    for (int cubeZ = 0; cubeZ < 16; cubeZ++) {
                        final int x = coordinateX + cubeX;
                        final int z = coordinateZ + cubeZ;

                        result = result.concat(" " + getNoiseFor(x, z, type) + " ");
                    }
                }

                System.out.println(result.trim());
            }
        }
    }

}
