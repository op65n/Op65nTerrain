package op65n.tech.terrain.common.terrain.noise;

import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.perlin.type.NoiseGenerator;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class NoiseCalculation {

    private final int x;
    private final int z;
    private final int maxHeight;
    private final int medianLevel;
    private final int seaLevel;
    private final long seed;
    private final NoiseType type;
    private final Set<TerrainAdjustment> adjustments = new HashSet<>();

    public NoiseCalculation(final int x, final int z, final int maxHeight, final int medianLevel, final int seaLevel,
                            final long seed, final NoiseType type, final TerrainAdjustment... adjustments) {
        this.x = x;
        this.z = z;
        this.maxHeight = maxHeight;
        this.medianLevel = medianLevel;
        this.seaLevel = seaLevel;
        this.seed = seed;
        this.type = type;
        this.adjustments.addAll(Arrays.asList(adjustments));
    }

    public int getHeight() {
        double noise = 0;

        for (final TerrainAdjustment adjustment : this.adjustments) {
            final NoiseGenerator generator = adjustment.getGenerator(this.type, this.seed);

            double lacunarity = adjustment.getLacunarity();
            double persistence = adjustment.getPersistence();

            final double perlin = generator.noise(this.x, this.z, adjustment.getOctaves(), lacunarity, persistence, false);

            switch (adjustment.getAdditionType()) {
                case PLUS -> noise += perlin;
                case MINUS -> noise -= perlin;
                case MULTIPLY -> noise *= perlin;
                case DIVIDE -> noise /= perlin;
            }
        }

        if (noise > this.maxHeight) {
            noise = this.maxHeight;
        }

        return (int) noise * this.seaLevel + this.medianLevel;
    }

}
