package com.these3000.core.scenes;

import java.util.Random;

import com.these3000.core.graphics.Shader;
import com.these3000.core.graphics.Texture;
import com.these3000.core.graphics.VertexArray;
import com.these3000.core.input.Input;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Scene1 {

	private Player player;
	private Tile[][] tiles;
	private static int NUM_X = 8;
	private static int NUM_Y = 8;

	int[][] map = new int[][] {
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 1, 1, 0, 0, 0 },
								{ 0, 0, 0, 1, 1, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 1, 1, 1, 1, 1, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0, },

	};

	public Scene1() {
		tiles = new Tile[NUM_X][NUM_Y];

		for (int y = 0; y < NUM_Y; y++) {
			for (int x = 0; x < NUM_X; x++) {
				Tile tile = null;
				int mapIndex = map[x][y];
				System.out.println(x + " " + y);
				if (mapIndex == 1) {
					tile = new Wall("res/wall2.png", 64, 32, x, y, 0);
				}
				else {
					tile = new GroundTile("res/tile1.png", 64, 32, x, y, 0);
				}

				tiles[x][y] = tile;
			}
		}

		player = new Player();

	}

	public void update() {
		player.setMap(map);
		player.update();

		Shader.BG.setUniform2f("bird", player.getIsoX(), player.getIsoY());
	}

	public void render() {

		for (int y = 0; y < NUM_Y; y++) {
			for (int x = 0; x < NUM_X; x++) {

				tiles[x][y].draw();
			}
		}

		player.render();

	}
}
