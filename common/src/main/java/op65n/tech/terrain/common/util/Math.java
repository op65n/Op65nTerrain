package op65n.tech.terrain.common.util;

public final class Math {

    public static double grad(final int hash, final double x, final double y, final double z) {
        switch (hash & 0xF) {
            case 0x0 -> {
                return x + y;
            }
            case 0x1 -> {
                return -x + y;
            }
            case 0x2 -> {
                return x - y;
            }
            case 0x3 -> {
                return -x - y;
            }
            case 0x4 -> {
                return x + z;
            }
            case 0x5 -> {
                return -x + z;
            }
            case 0x6 -> {
                return x - z;
            }
            case 0x7 -> {
                return -x - z;
            }
            case 0x8 -> {
                return y + z;
            }
            case 0x9, 0xD -> {
                return -y + z;
            }
            case 0xA -> {
                return y - z;
            }
            case 0xB, 0xF -> {
                return -y - z;
            }
            case 0xC -> {
                return y + x;
            }
            case 0xE -> {
                return y - x;
            }
            default -> {
                return 0;
            } // never happens
        }
    }

    public static double fade(final double value) {
        return value * value * value * (value * (value * 6 - 15) + 10);
    }

    public static double lerp(final double valueA, final double valueB, final double x) {
        return valueA + x * (valueB - valueA);
    }

    public static double map(final double value, final double inMinimum, final double inMaximum, final double outMinimum, final double outMaximum) {
        return (value - inMinimum) * (outMaximum - outMinimum + 1) / (inMaximum - inMinimum + 1) + outMinimum;
    }

}
