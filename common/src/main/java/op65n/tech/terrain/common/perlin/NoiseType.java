package op65n.tech.terrain.common.perlin;

import op65n.tech.terrain.common.perlin.type.NoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.ImprovedPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.OriginalPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.SimplePerlinNoiseGenerator;

public enum NoiseType {

    SIMPLE(), IMPROVED(), ORIGINAL();

    public static NoiseGenerator getTypeFor(final NoiseType type, final long seed) {
        switch (type) {
            case SIMPLE: return new SimplePerlinNoiseGenerator(seed);
            case IMPROVED: return new ImprovedPerlinNoiseGenerator(seed);
            case ORIGINAL: return null;
        }

        return null;
    }

}
