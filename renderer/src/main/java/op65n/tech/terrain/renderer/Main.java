package op65n.tech.terrain.renderer;

import com.raylib.Jaylib;
import op65n.tech.terrain.common.perlin.type.impl.ImprovedPerlinNoiseGenerator;
import op65n.tech.terrain.common.perlin.type.impl.SimplePerlinNoiseGenerator;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;

import java.util.Random;
import java.util.SplittableRandom;

public final class Main {
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final int SEA_LEVEL = 60;
    private static final int MEDIAN_LEVEL = 72;

    private final TerrainAdjustment elevation = new TerrainAdjustment.AdjustmentBuilder()
            .scale(1 / 800D).build();
    private final TerrainAdjustment detail = new TerrainAdjustment.AdjustmentBuilder()
            .pow(1, 1).scale(1 / 50D).build();
    private final TerrainAdjustment rough = new TerrainAdjustment.AdjustmentBuilder()
            .pow(2, 2).additionType(TerrainAdjustment.AdditionType.MULTIPLY).octaves(1).scale(1 / 200D).build();
    public static void main(String[] args) {
        Display display = new Display(
                800,
                450,
                60,
                new Jaylib.Vector3(18,16,18),
                new Jaylib.Vector3(0,1,0),
                "Terrain Renderer",
                new TerrainMap(new ImprovedPerlinNoiseGenerator(new Random()))
        );
        display.run();
    }
}
