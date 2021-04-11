package op65n.tech.terrain.renderer;

import com.raylib.Jaylib;
import com.raylib.Raylib;

import static com.raylib.Jaylib.*;

public final class Display implements Runnable {

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

            ClearBackground(LIGHTGRAY);
            UpdateCamera(camera);
            BeginDrawing();
            BeginMode3D(camera);

            terrainMap.generateAround(
                    (int) camera._position().x(),
                    (int) camera._position().z(),
                    50,
                    this::onGenerationRequest
            );

            EndMode3D();

            DrawFPS(20, 20);
            EndDrawing();

        }
        Raylib.CloseWindow();
    }

    private void onGenerationRequest(int x, int z, int y) {
        final Raylib.Vector3 position = new Jaylib.Vector3(x, y, z);

        DrawCube(position, 1f, 1f, 1f, DARKGREEN);
        DrawCubeWires(position, 1f, 1f, 1f, BLACK);

        y -= 1;
        for (; y > 0; y--) {
            position.y(y);

            DrawCube(position, 1f, 1f, 1f, GRAY);
            DrawCubeWires(position, 1f, 1f, 1f, BLACK);
        }

        position.releaseReference();
    }
}
