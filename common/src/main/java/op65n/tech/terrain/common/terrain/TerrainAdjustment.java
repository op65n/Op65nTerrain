package op65n.tech.terrain.common.terrain;

import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.perlin.type.NoiseGenerator;

public final class TerrainAdjustment {

    private final double  scale;

    private final int octaves;

    private final double lacunarity;
    private final double persistence;

    private final int lacunarityPow;
    private final int persistencePow;

    private final AdditionType additionType;

    TerrainAdjustment(final double scale, final int octaves, final double lacunarity, final double persistence,
                      final int lacunarityPow, final int persistencePow, final AdditionType additionType) {
        this.scale = scale;
        this.octaves = octaves;

        this.lacunarity = lacunarity;
        this.persistence = persistence;

        this.lacunarityPow = lacunarityPow;
        this.persistencePow = persistencePow;

        this.additionType = additionType;
    }

    public double getScale() {
        return this.scale;
    }

    public int getOctaves() {
        return this.octaves;
    }

    public double getLacunarity() {
        return Math.pow(this.lacunarity, lacunarityPow);
    }

    public double getPersistence() {
        return Math.pow(this.persistence, persistencePow);
    }

    public AdditionType getAdditionType() {
        return this.additionType;
    }

    public NoiseGenerator getGenerator(final NoiseType type, final long seed) {
        return NoiseType.getTypeFor(type, seed);
    }

    public static final class AdjustmentBuilder {

        private double lacunarity = 2.0;
        private double persistence = 0.5;
        private double scale = 50;

        private int octaves = 3;

        private int lacunarityPow = 0;
        private int persistencePow = 0;

        private AdditionType additionType = AdditionType.PLUS;

        public AdjustmentBuilder lacunarity(final double value) {
            this.lacunarity = value;
            return this;
        }

        public AdjustmentBuilder persistence(final double value) {
            this.persistence = value;
            return this;
        }

        public AdjustmentBuilder scale(final double value) {
            this.scale = value;
            return this;
        }

        public AdjustmentBuilder octaves(final int value) {
            this.octaves = value;
            return this;
        }

        public AdjustmentBuilder lacunarityPow(final int value) {
            this.lacunarityPow = value;
            return this;
        }

        public AdjustmentBuilder persistencePow(final int value) {
            this.persistencePow = value;
            return this;
        }

        public AdjustmentBuilder pow(final int lacunarity, final int persistence) {
            this.lacunarityPow = lacunarity;
            this.persistencePow = persistence;
            return this;
        }

        public AdjustmentBuilder additionType(final AdditionType value) {
            this.additionType = value;
            return this;
        }

        public TerrainAdjustment build() {
            return new TerrainAdjustment(scale, octaves, persistence, lacunarity, lacunarityPow, persistencePow, additionType);
        }

    }

    public enum AdditionType {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }

}
