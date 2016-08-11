package com.these3000.core.scenes;

import com.these3000.core.entities.GroundTile;
import com.these3000.core.entities.Player;
import com.these3000.core.entities.Tile;
import com.these3000.core.entities.Wall;
import com.these3000.core.graphics.Shader;

public class Scene1 {

	private Player player;
	private Tile[][] tiles;
	private static int NUM_X = 8;
	private static int NUM_Y = 8;

	int[][] map = new int[][] {
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0 },
								{ 0, 0, 0, 3, 1, 0, 0, 0 },
								{ 0, 0, 0, 2, 3, 0, 0, 0 },
								{ 0, 0, 0, 2, 0, 0, 0, 0 },
								{ 3, 1, 1, 4, 3, 0, 0, 0 },
								{ 0, 0, 0, 0, 0, 0, 0, 0, },

	};

	public Scene1() {
		tiles = new Tile[NUM_X][NUM_Y];

		for (int y = 0; y < NUM_Y; y++) {
			for (int x = 0; x < NUM_X; x++) {
				Tile tile = null;
				int mapIndex = map[x][y];
				System.out.println(x + " " + y);
				switch (mapIndex) {
				case 1:
					tile = new Wall("res/earth1_wall_right.png", 64, 32, x, y, 0);

					break;
				case 2:
					tile = new Wall("res/earth1_wall_left.png", 64, 32, x, y, 0);

					break;
				case 3:
					tile = new Wall("res/earth1_wall_corner_right_left.png", 64, 32, x, y, 0);

					break;
				case 4:
					tile = new Wall("res/earth1_wall_corner_left_right.png", 64, 32, x, y, 0);

					break;
				default:
					tile = new GroundTile("res/grass1_floor.png", 64, 32, x, y, 0);
					break;
				}

				tiles[x][y] = tile;
			}
		}

		player = new Player();

	}

	public void update() {
		player.setMap(map);
		player.update();

		Shader._TILE.setUniform2f("player", player.getIsoX(), player.getIsoY());
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
