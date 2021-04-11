package op65n.tech.terrain.renderer;

import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;
import op65n.tech.terrain.common.terrain.noise.NoiseCalculation;

public final class TerrainMap {

    private static final int MAX_HEIGHT = 255;
    private static final int SEED = 68304683;

    private static final TerrainAdjustment ELEVATION = new TerrainAdjustment.AdjustmentBuilder()
            .lacunarity(1).scale(1 / 800D).build();
    private static final TerrainAdjustment DETAIL = new TerrainAdjustment.AdjustmentBuilder()
            .lacunarity(1).pow(1, 1).scale(1 / 50D).build();
    private static final TerrainAdjustment ROUGH = new TerrainAdjustment.AdjustmentBuilder()
            .lacunarity(1).pow(2, 2).additionType(TerrainAdjustment.AdditionType.MULTIPLY)
            .octaves(1).scale(1 / 200D).build();

    public void generateAround(int x, int z, int renderDistance, Callback renderCallback) {
        int minX = x - renderDistance;
        int maxX = x + renderDistance;

        int minZ = z - renderDistance;
        int maxZ = z + renderDistance;

        for (int coordinateX = minX; coordinateX <= maxX; coordinateX++) {
            for (int coordinateZ = minZ; coordinateZ <= maxZ; coordinateZ++) {
                final NoiseCalculation calculation = new NoiseCalculation(
                        coordinateX, coordinateZ, MAX_HEIGHT, SEED, NoiseType.IMPROVED,
                        ELEVATION, ROUGH, DETAIL
                );

                final int height = calculation.getHeight();

                renderCallback.onGenerationRequest(coordinateX, coordinateZ, height);
            }
        }
    }

    @FunctionalInterface
    interface Callback {
        void onGenerationRequest(int x, int y, int z);
    }
}