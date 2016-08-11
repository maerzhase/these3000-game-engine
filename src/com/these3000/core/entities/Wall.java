package com.these3000.core.entities;

import com.these3000.core.graphics.Shader;
import com.these3000.core.graphics.Texture;
import com.these3000.core.graphics.VertexArray;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;

public class Wall extends Tile {

	private VertexArray _tile;
	private Texture _tileTexture;
	private float _width;
	private float _height;
	private Vector3f _position;

	public Wall(String tex, float width, float height, int x, int y, float z) {

		// float res = width / height;

		float cartX = -x * (width / 2);
		float cartY = -y * (height);
		_position = new Vector3f(cartX, cartY, 0);
		_width = width;
		_height = height;
		float[] vertices = new float[] {
										-_width / 2.0f, -_height / 2.0f, -0.5f,
										-_width / 2.0f, _height / 2.0f, -0.5f,
										_width / 2.0f, _height / 2.0f, -0.5f,
										_width / 2.0f, -_height / 2.0f, -0.5f,

		};

		byte[] indices = new byte[] {
										0, 1, 2,
										2, 3, 0
		};

		float[] tcs = new float[] {
									0, 0,
									0, 1,
									1, 1,
									1, 0
		};
		_tile = new VertexArray(vertices, indices, tcs);
		_tileTexture = new Texture(tex);

	}

	public Vector3f getIso() {

		float isoX = _position.x - _position.y;
		float isoY = (_position.x + _position.y) / 2;
		return new Vector3f(isoX, isoY, _position.z);
	}

	public void update(Vector3f charPos) {

	}

	public void draw() {
		Vector3f isoPos = this.getIso();

		_tileTexture.bind();
		Shader._TILE.enable();
		_tile.bind();
		Shader._TILE.setUniformMat4f("vw_matrix",
				Matrix4f.translate(new Vector3f(isoPos.x, isoPos.y, 0.0f)));
		_tile.draw();
		Shader._TILE.disable();
		_tileTexture.unbind();

	}

}
