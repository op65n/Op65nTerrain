package op65n.tech.terrain.renderer;

import com.raylib.Jaylib;

public final class Main {

    public static void main(String[] args) {
        Display display = new Display(
                800,
                450,
                60,
                new Jaylib.Vector3(0, 20, 0),
                new Jaylib.Vector3(0, 1, 0),
                "Terrain Renderer",
                new TerrainMap()
        );
        display.run();
    }
}
