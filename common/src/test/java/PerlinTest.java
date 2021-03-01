import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.perlin.type.NoiseGenerator;

import java.text.DecimalFormat;

public final class PerlinTest {

    private static final int size = 20;
    private static final DecimalFormat FORMAT = new DecimalFormat("###.##");

    public static void main(final String[] input) {
        testPerlin();
    }

    private static void testPerlin() {
        final NoiseGenerator generator = NoiseType.getTypeFor(NoiseType.SIMPLE, 0);

        for (int z = -size; z <= size; z++) {
            String result = "";

            for (int x = -size; x <= size; x++) {
                result = result.concat(" " + FORMAT.format(generator.noise(x, z)) + " ");
            }

            System.out.println(result.trim());
        }
    }

}
