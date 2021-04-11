package op65n.tech.terrain.renderer;

import com.raylib.Jaylib;
import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.perlin.type.NoiseGenerator;
import op65n.tech.terrain.common.terrain.noise.NoiseCalculation;

public final class TerrainMap {
    private final NoiseGenerator noiseGenerator;

    public TerrainMap(NoiseGenerator noiseGenerator) {
        this.noiseGenerator = noiseGenerator;
    }

    public void generateAround(int x, int z, int renderDistance, Callback renderCallback) {

        int minX = x - renderDistance;
        int maxX = x + renderDistance;

        int minZ = z - renderDistance;
        int maxZ = z + renderDistance;

        for (int i = minX; i <= maxX; i++) {
            for (int j = minZ; j <= maxZ; j++) {
                final NoiseCalculation calculation = new NoiseCalculation(
                        i, j, 300, 60, 30, 45643, NoiseType.IMPROVED);
                renderCallback.onGenerationRequest(i, j, calculation.getHeight());
            }
        }
    }

    @FunctionalInterface
    interface Callback {
        void onGenerationRequest(int x, int y, double z);
    }
}