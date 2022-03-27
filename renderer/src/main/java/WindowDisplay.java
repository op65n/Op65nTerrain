import com.raylib.Jaylib;
import com.raylib.Raylib;
import op65n.tech.terrain.common.noise.height.HeightMap;
import op65n.tech.terrain.common.util.Math;

import java.awt.Color;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public final class WindowDisplay {

    private static final int RENDER_DISTANCE = 100;
    private static final int SCREEN_WIDTH = 1400;
    private static final int SCREEN_HEIGHT = 600;

    private static final Vector3 CAMERA_POSITION = new Jaylib.Vector3(18.0f, 16.0f, 18.0f);
    private static final Vector3 CAMERA_ROTATION = new Jaylib.Vector3(0.0f, 1.0f, 0.0f);

    public static void main(final String[] args) {
        new WindowDisplay().init();
    }

    private boolean close = false;

    public void init() {
        InitWindow(SCREEN_WIDTH, SCREEN_HEIGHT, "Terrain Mapper");
        SetTargetFPS(144);

        final Camera3D camera = new Jaylib.Camera(CAMERA_POSITION, new Jaylib.Vector3(), CAMERA_ROTATION, 45.0f, 0);
        SetCameraMode(camera, CAMERA_FREE); // CAMERA_ORBITAL, CAMERA_FREE

        while (!WindowShouldClose()) {
            UpdateCamera(camera);

            if (IsKeyDown('Z')) camera.target(new Jaylib.Vector3(0.0f, 0.0f, 0.0f));

            BeginDrawing();
                ClearBackground(RAYWHITE);

                BeginMode3D(camera);

                    DrawGrid(RENDER_DISTANCE, 1.0f);

                    DrawCube(new Jaylib.Vector3(0, 5, 0), 1.5f, 7.0f, 1.5f, RED);
                    DrawSphere(new Jaylib.Vector3(-1.5f, 0.5f, 0), 2.0f, RED);
                    DrawSphere(new Jaylib.Vector3(1.5f, 0.5f, 0), 2.0f, RED);

                EndMode3D();

                DrawRectangle( 10, 10, 320, 150, Fade(SKYBLUE, 0.5f));
                DrawRectangleLines( 10, 10, 320, 150, BLUE);

                DrawText("Camera Controls:", 20, 20, 10, BLACK);
                DrawText("- Mouse Wheel to Zoom in-out", 40, 40, 10, DARKGRAY);
                DrawText("- Mouse Wheel Pressed to Pan", 40, 60, 10, DARKGRAY);
                DrawText("- Alt + Mouse Wheel Pressed to Rotate", 40, 80, 10, DARKGRAY);
                DrawText("- Alt + Ctrl + Mouse Wheel Pressed for Smooth Zoom", 40, 100, 10, DARKGRAY);
                DrawText("- Z to zoom to (0, 0, 0)", 40, 120, 10, DARKGRAY);

                DrawText(String.format("- Current FPS: %s", GetFPS()), 40, 140, 10, DARKGRAY);
            EndDrawing();

            if (WindowShouldClose()) close = true;
        }

        if (close) CloseWindow();
    }

    private void drawTerrain(final int x, final int z) {
        final TerrainMesh mesh = new TerrainMesh(x, z);

        final Mesh meshModel = GenMeshCube(1, 1, 1);
        final Model model = LoadModelFromMesh(meshModel);

        for (final TerrainMesh.TerrainBlock block : mesh.populate(RENDER_DISTANCE)) {
            final int blockX = block.getX();
            final int blockZ = block.getZ();
            final int blockY = block.getMappedY();

            for (int height = blockY - 2; height <= blockY; height++) {
                final Raylib.Color color = getColor(HeightMap.getHeightMapColor(Math.map(height, 0, 30, -1, 1)));

                DrawModelWires(model, new Jaylib.Vector3(blockX, height, blockZ), 1f, color);
                //DrawModel(model, new Jaylib.Vector3(blockX, height, blockZ), 1f, color);
            }
        }
    }

    private Raylib.Color getColor(final Color color) {
        return (new Raylib.Color()).r((byte) color.getRed()).g((byte) color.getGreen()).b((byte) color.getBlue()).a((byte) color.getAlpha());
    }

}
