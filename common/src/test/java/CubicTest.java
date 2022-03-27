import op65n.tech.terrain.common.noise.height.HeightMap;
import op65n.tech.terrain.common.noise.impl.CubicNoise;

import java.awt.*;
import java.text.DecimalFormat;

public final class CubicTest {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.#");
    private static final int SIZE = 20;

    public static void main(final String[] args) {
        final CubicNoise noiseGenerator = new CubicNoise(1232135235, 7);

        for (int z = -SIZE; z <= SIZE; z++) {
            String result = "";

            for (int x = -SIZE; x <= SIZE; x++) {
                final double value = noiseGenerator.sample(x, z);
                final Color color = HeightMap.getHeightMapColor(value);

                result = result.concat(" ").concat(FORMAT.format(value)).concat(" ");
                //result = result.concat(String.format(" \\033[38;2;%s;%s;%sm■ ", color.getRed(), color.getGreen(), color.getBlue()));
            }

            System.out.println(result);
        }
    }

}
