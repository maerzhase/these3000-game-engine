package com.these3000.core.scenes;

import com.these3000.core.graphics.Shader;
import com.these3000.core.graphics.Texture;
import com.these3000.core.graphics.VertexArray;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;

public abstract class Tile {
	private VertexArray _tile;
	private Texture _tileTexture;
	private float _width;
	private float _height;
	private Vector3f _position;

	public void update(Vector3f charPos) {

	}

	public void draw() {

	}
}
