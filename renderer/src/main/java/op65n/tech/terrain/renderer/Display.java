package op65n.tech.terrain.renderer;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import op65n.tech.terrain.common.perlin.NoiseType;
import op65n.tech.terrain.common.terrain.TerrainAdjustment;
import op65n.tech.terrain.common.terrain.noise.NoiseCalculation;

import java.util.SplittableRandom;

import static com.raylib.Jaylib.*;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Raylib.DrawCubeWires;
import static com.raylib.Raylib.DrawFPS;
import static com.raylib.Raylib.DrawText;
import static com.raylib.Raylib.EndDrawing;
import static com.raylib.Raylib.EndMode3D;

public final class Display implements Runnable {
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 300;
    private static final SplittableRandom RANDOM = new SplittableRandom();
    private static final int SEA_LEVEL = 60;
    private static final int MEDIAN_LEVEL = 72;
    private final TerrainAdjustment elevation = new TerrainAdjustment.AdjustmentBuilder()
            .scale(1 / 800D).build();
    private final TerrainAdjustment detail = new TerrainAdjustment.AdjustmentBuilder()
            .pow(1, 1).scale(1 / 50D).build();
    private final TerrainAdjustment rough = new TerrainAdjustment.AdjustmentBuilder()
            .pow(2, 2).additionType(TerrainAdjustment.AdditionType.MULTIPLY).octaves(1).scale(1 / 200D).build();
    private final int width;
    private final int height;
    private final int targetFps;
    private final Jaylib.Vector3 cameraPosition;
    private final Jaylib.Vector3 cameraRotation;
    private final String windowName; /*new Jaylib.Vector3(18,16,18)*/ /* new Jaylib.Vector3(0,1,0) */

    private final TerrainMap terrainMap;

    public Display(int width, int height, int targetFps, Jaylib.Vector3 cameraPosition, Jaylib.Vector3 cameraRotation, String windowName, TerrainMap terrainMap) {
        this.width = width;
        this.height = height;
        this.targetFps = targetFps;
        this.cameraPosition = cameraPosition;
        this.cameraRotation = cameraRotation;
        this.windowName = windowName;
        this.terrainMap = terrainMap;
    }

    @Override
    public void run() {
        InitWindow(width, height, windowName);
        SetTargetFPS(targetFps);
        Camera3D camera = new Camera(cameraPosition, new Jaylib.Vector3(), cameraRotation, 45, 0);
        SetCameraMode(camera, CAMERA_FIRST_PERSON);

        while (!WindowShouldClose()) {
            UpdateCamera(camera);
            BeginDrawing();
            ClearBackground(RAYWHITE);
            BeginMode3D(camera);
            DrawGrid(20, 1.0f);
            terrainMap.generateAround(
                    (int) camera._position().x(),
                    (int) camera._position().z(),
                    20,
                    this::onGenerationRequest
            );
            EndMode3D();
            DrawFPS(20, 20);
            EndDrawing();
        }
        Raylib.CloseWindow();
    }

    private void onGenerationRequest(int x, int z, double yf) {
        int y = (int) ((MAX_Y - MIN_Y) * yf + MIN_Y);
        Jaylib.Vector3 position = new Jaylib.Vector3(x, y, z);
        DrawCube(position, 0.8f, 0.8f, 0.8f, GREEN);
        DrawCubeWires(position, 0.8f, 0.8f, 0.8f, BLACK);
        position.releaseReference();
    }
}
