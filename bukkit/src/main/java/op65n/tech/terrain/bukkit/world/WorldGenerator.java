package op65n.tech.terrain.bukkit.world;

import op65n.tech.terrain.bukkit.util.ChunkFile;
import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;
import op65n.tech.terrain.common.terrain.noise.NoiseCalculation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.SplittableRandom;

public final class WorldGenerator extends ChunkGenerator {

    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final int SEA_LEVEL = 60;
    private static final int MEDIAN_LEVEL = 72;

    private final TerrainAdjustment elevation = new TerrainAdjustment.AdjustmentBuilder()
            .scale(1 / 800D).build();
    private final TerrainAdjustment detail = new TerrainAdjustment.AdjustmentBuilder()
            .pow(1, 1).scale(1 / 50D).build();
    private final TerrainAdjustment rough = new TerrainAdjustment.AdjustmentBuilder()
            .pow(2, 2).additionType(TerrainAdjustment.AdditionType.MULTIPLY).octaves(1).scale(1 / 200D).build();

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull final World world, @NotNull final Random random, final int chunkX, final int chunkZ, @NotNull final BiomeGrid biome) {
        final ChunkData data = createChunkData(world);

        for (int z = 0; z < 16; z++) {
            for (int x = 0; x < 16; x++) {
                final int coordinateX = chunkX >> 4 + x;
                final int coordinateZ = chunkZ >> 4 + z;

                final NoiseCalculation calculation = new NoiseCalculation(
                        coordinateX, coordinateZ, world.getMaxHeight(), world.getSeed(), NoiseType.IMPROVED,
                        elevation, detail, rough
                );

                final int height = calculation.getHeight();

                generateBedrock(data, x, z);
                generateTerrain(data, x, height, z);
                generateSeaLevel(data, x, z);
            }
        }

        ChunkFile.assignData(chunkX, chunkZ, data);

        return data;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return true;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return true;
    }

    private void generateTerrain(final ChunkData data, final int x, final int height, final int z) {
        for (int y = 0; y <= height; y++) {
            if (y == height)
                data.setBlock(x, y, z, Material.GRASS_BLOCK);

            if (y < height && y > height - 4)
                data.setBlock(x, y, z, Material.DIRT);

            if (y < height - 4)
                data.setBlock(x, y, z, Material.STONE);
        }
    }

    private void generateSeaLevel(final ChunkData data, final int x, final int z) {
        for (int y = 5; y <= SEA_LEVEL; y++) {
            if (data.getType(x, y, z) != Material.AIR)
                continue;

            data.setBlock(x, y, z, Material.WATER);
        }
    }

    private void generateBedrock(final ChunkData data, final int x, final int z) {
        final int height = RANDOM.nextInt(0, 6);

        for (int y = 0; y <= height; y++) {
            data.setBlock(x, y, z, Material.BEDROCK);
        }
    }
}
