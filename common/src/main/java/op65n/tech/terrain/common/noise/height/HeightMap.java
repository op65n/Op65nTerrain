package op65n.tech.terrain.common.noise.height;

import java.awt.*;

public enum HeightMap {

    SNOW_2(0.92, 1.1, new Color(219, 219, 219)),
    SNOW_1(0.86, 0.92, new Color(112, 112, 112)),
    HIGH_4(0.8, 0.86, new Color(34, 21, 1)),
    HIGH_3(0.73, 0.8, new Color(38, 22, 9)),
    HIGH_2(0.64, 0.73, new Color(41, 26, 13)),
    HIGH_1(0.53, 0.64, new Color(59, 33, 21)),
    MEDIUM_4(0.46, 0.53, new Color(50, 36, 27)),
    MEDIUM_3(0.4, 0.46, new Color(52, 38, 30)),
    MEDIUM_2(0.325, 0.4, new Color(64, 55, 36)),
    MEDIUM_1(0.25, 0.325, new Color(76, 62, 44)),
    LOW_5(0.2, 0.25, new Color(15, 76, 5)),
    LOW_4(0.15, 0.2, new Color(35, 105, 8)),
    LOW_3(0.1, 0.15, new Color(66, 150, 6)),
    LOW_2(0.05, 0.1, new Color(159, 132, 57)),
    LOW_1(0, 0.05, new Color(191, 178, 77)),
    SHALLOW_1(-0.3, 0, new Color(5, 115, 150)),
    SHALLOW_2(-0.55, -0.3, new Color(4, 63, 112)),
    DEEP_1(-0.7, -0.55, new Color(3, 37, 105)),
    DEEP_2(-0.85, -0.7, new Color(5, 32, 157)),
    DEEP_3(-1.1, -0.85, new Color(0, 23, 110));

    private final double from;
    private final double to;
    private final Color color;

    HeightMap(final double from, final double to, final Color color) {
        this.from = from;
        this.to = to;
        this.color = color;
    }

    public static Color getHeightMapColor(final double value) {
        Color result = Color.DARK_GRAY;

        for (final HeightMap heightMap : values()) {
            final double from = heightMap.from;
            final double to = heightMap.to;

            if (value > from && value <= to) {
                result = heightMap.color;
                break;
            }
        }

        return result;
    }

}
