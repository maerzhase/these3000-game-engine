package com.these3000.core.entities;

import static org.lwjgl.glfw.GLFW.*;

import com.these3000.core.graphics.Shader;
import com.these3000.core.graphics.Texture;
import com.these3000.core.graphics.VertexArray;
import com.these3000.core.input.Input;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;

public class Player {

	private float _width = 64f;
	private float _height = 64f;
	private float _Z = 20f;
	private VertexArray _mesh;
	private Texture _texture;

	private Vector3f _position = new Vector3f();

	private float _speed = 1;
	private float _jump = 0;
	private float _lastX = 0;
	private float _lastY = 0;
	private boolean _isJumping = false;
	int[][] _map = new int[8][8];

	public Player() {
		float[] vertices = new float[] {
										-_width / 2.0f, -_height / 2.0f, _Z,
										-_width / 2.0f, _height / 2.0f, _Z,
										_width / 2.0f, _height / 2.0f, _Z,
										_width / 2.0f, -_height / 2.0f, _Z
		};

		byte[] indices = new byte[] {
										0, 1, 2,
										2, 3, 0
		};

		float[] tcs = new float[] {
									0, 1,
									0, 0,
									1, 0,
									1, 1
		};

		_mesh = new VertexArray(vertices, indices, tcs);
		_texture = new Texture("res/char.png");
	}

	public void setMap(int[][] map) {
		this._map = map;
	}

	public void update() {

		if (_isJumping && _lastY <= _position.y) {
			_jump -= _speed / 10;
		} else {
			_isJumping = false;
			_jump = 0;
		}
		if (!_isJumping) {
			_lastX = _position.x;
			_lastY = _position.y;
		}

		if (Input.isKeyDown(GLFW_KEY_SPACE) && !_isJumping) {
			_jump = _speed * 2;
			_lastX = _position.x;
			_lastY = _position.y;
			_isJumping = true;
		}

		if (Input.isKeyDown(GLFW_KEY_UP))
			_position.y += _speed;
		if (Input.isKeyDown(GLFW_KEY_DOWN))
			_position.y -= _speed;
		if (Input.isKeyDown(GLFW_KEY_LEFT))
			_position.x -= _speed;
		if (Input.isKeyDown(GLFW_KEY_RIGHT))
			_position.x += _speed;

		_position.y += _jump;

		// int cordX = (int) (getX());
		// int cordY = (int) (getY());
		int cordX = (int) (((getCartX() * -1) + 32) / 32);
		int cordY = (int) (((getCartY() * -1) + 32) / 32);
		int mapIndex = 0;
		if (cordX < 8 && cordX < 8) {
			mapIndex = _map[cordX][cordY];
		}
		if (mapIndex > 0) {
			_position.x = _lastX;
			_position.y = _lastY;
		}
		System.out.println(mapIndex + " x: " + cordX + " y: " + cordY);

		// rot = -delta * 90.0f;
	}

	public void render() {
		Shader._PLAYER.enable();

		// Vector3f rotPos = position.rotate(new Vector3f(0f, 0f, 1f), 45);
		// rotPos = rotPos.rotate(new Vector3f(1f,0f,0f), -60)
		Matrix4f ml_matrix = Matrix4f.translate(new Vector3f(getIsoX(), getIsoY(), 0));
		// ml_matrix = Matrix4f.translate(getPos(45));

		// ml_matrix = ml_matrix.multiply(Matrix4f.rotateZ(-45));
		// ml_matrix = ml_matrix.multiply(Matrix4f.rotateX(-60));
		Shader._PLAYER.setUniformMat4f("ml_matrix", ml_matrix);
		_texture.bind();
		_mesh.render();
		Shader._PLAYER.disable();
	}

	public float getIsoY() {
		float isoY = (_position.x + _position.y) / 2;
		return isoY;
	}

	public float getIsoX() {
		float isoX = _position.x - _position.y;
		return isoX;
	}

	public float getCartY() {
		float isoX = getIsoX();
		float isoY = getIsoY();
		float cartY = (2 * isoY - isoX) / 2;
		return cartY;
	}

	public float getCartX() {
		float isoX = getIsoX();
		float isoY = getIsoY();
		float cartX = (2 * isoY + isoX) / 2;
		return cartX;
	}

	public Vector3f getPos(float rot) {
		return new Vector3f(getIsoX(), getIsoY(), 0).rotate(new Vector3f(0, 0, 1), rot);
	}

	public float getY() {
		return _position.y;
	}

	public float getX() {

		return _position.x;
	}

	public float getSize() {
		return _width;
	}

}
