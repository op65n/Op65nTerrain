package op65n.tech.terrain.common.noise.impl;

import op65n.tech.terrain.common.noise.NoiseGenerator;
import op65n.tech.terrain.common.noise.component.Octave;
import op65n.tech.terrain.common.util.Math;

import java.util.Set;

public final class ImprovedPerlinNoise extends NoiseGenerator {

    private static final int[] permutation = new int[512];

    static {
        for (int index = 0; index < 512; index++) {
            permutation[index] = getDefaultHashPermutation()[index % 256];
        }
    }

    @Override
    public double getNoise(final double x, final double z, final Set<Octave> octaves, final double scale) {
        return getNoise(x, 0, z, octaves, scale);
    }

    @Override
    public double getNoise(double x, double y, double z, final Set<Octave> octaves, double scale) {
        if (scale <= 0)
            scale = 1;

        x = x / scale;
        y = y / scale;
        z = z / scale;

        double result = 0;
        double maxValue = 0;

        for (final Octave octave : octaves) {
            final double frequency = octave.getFrequency();
            final double amplitude = octave.getAmplitude();

            result += noise(x * frequency, y * frequency, z * frequency) * amplitude;
            maxValue += amplitude;
        }

        return result / maxValue;
    }

    @Override
    public double noise(final double x, final double y, final double z) {
        final int xIndex = (int) x & 255;
        final int yIndex = (int) y & 255;
        final int zIndex = (int) z & 255;

        final double xFade = x - (int) x;
        final double yFade = y - (int) y;
        final double zFade = z - (int) z;

        final double u = Math.fade(xFade);
        final double v = Math.fade(yFade);
        final double w = Math.fade(zFade);

        int aaa, aba, aab, abb, baa, bba, bab, bbb;
        aaa = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        aba = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        aab = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        abb = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        baa = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        bba = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        bab = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];
        bbb = permutation[permutation[permutation[xIndex] + yIndex] + zIndex];

        double x1, x2, y1, y2;
        x1 = Math.lerp(Math.grad(aaa, xFade, yFade, zFade), Math.grad(baa, xFade - 1, yFade, zFade), u);
        x2 = Math.lerp(Math.grad(aba, xFade, yFade - 1, zFade), Math.grad(bba, xFade - 1, yFade - 1, zFade), u);

        y1 = Math.lerp(x1, x2, v);

        x1 = Math.lerp(Math.grad(aab, xFade, yFade, zFade - 1), Math.grad(bab, xFade - 1, yFade, zFade - 1), u);
        x2 = Math.lerp(Math.grad(abb, xFade, yFade - 1, zFade - 1), Math.grad(bbb, xFade - 1, yFade - 1, zFade - 1), u);

        y2 = Math.lerp(x1, x2, v);

        return (Math.lerp(y1, y2, w) + 1) / 2;
    }

}
