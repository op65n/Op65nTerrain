package op65n.tech.terrain.common.perlin;

import op65n.tech.terrain.common.perlin.type.NoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.ImprovedPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.OriginalPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.SimplePerlinNoiseGenerator;

public enum NoiseType {

    SIMPLE(), IMPROVED(), ORIGINAL();

    public static NoiseGenerator getTypeFor(final NoiseType type, final long seed) {
        return switch (type) {
            case SIMPLE -> new SimplePerlinNoiseGenerator(seed);
            case IMPROVED -> new ImprovedPerlinNoiseGenerator(seed);
            case ORIGINAL -> null;
        };
    }

}
