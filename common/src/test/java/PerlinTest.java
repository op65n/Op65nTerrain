import op65n.tech.terrain.common.noise.component.Octave;
import op65n.tech.terrain.common.noise.impl.ImprovedPerlinNoise;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Set;

public final class PerlinTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");
    private static final Set<Octave> OCTAVES = Set.of(
            new Octave(128, 4),
            new Octave(64, 8),
            new Octave(32, 16)
            //new Octave(16, 32),
            //new Octave(8, 64),
            //new Octave(4, 128)
    );

    public static void main(final String[] args) {
        final ImprovedPerlinNoise noiseGenerator = new ImprovedPerlinNoise();

        for (int z = -10; z <= 10; z++) {
            String result = "";

            for (int x = -10; x <= 10; x++) {
                final double value = noiseGenerator.getNoise(x, z, OCTAVES, 1.01) * 100;

                result = result.concat(" " + (int) value + " ");
            }

            System.out.println(result);
        }
    }

}
