package com.these3000.core.scenes;

import static org.lwjgl.glfw.GLFW.*;

import com.these3000.core.graphics.Shader;
import com.these3000.core.graphics.Texture;
import com.these3000.core.graphics.VertexArray;
import com.these3000.core.input.Input;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;

public class Player {

	private float width = 64f;
	private float height = 64f;
	private float Z = 20f;
	private VertexArray mesh;
	private Texture texture;

	private Vector3f position = new Vector3f();
	private float rot;
	private float delta = 0.0f;

	private float speed = 1;
	private float jump = 0;
	private float lastX = 0;
	private float lastY = 0;
	private boolean isJumping = false;
	int[][] map = new int[8][8];

	public Player() {
		float[] vertices = new float[] {
										-width / 2.0f, -height / 2.0f, Z,
										-width / 2.0f, height / 2.0f, Z,
										width / 2.0f, height / 2.0f, Z,
										width / 2.0f, -height / 2.0f, Z
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

		mesh = new VertexArray(vertices, indices, tcs);
		texture = new Texture("res/char.png");
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public void update() {

		if (isJumping && lastY <= position.y) {
			jump -= speed / 10;
		} else {
			isJumping = false;
			jump = 0;
		}
		if (!isJumping) {
			lastX = position.x;
			lastY = position.y;
		}

		if (Input.isKeyDown(GLFW_KEY_SPACE) && !isJumping) {
			jump = speed * 2;
			lastX = position.x;
			lastY = position.y;
			isJumping = true;
		}

		if (Input.isKeyDown(GLFW_KEY_UP))
			position.y += speed;
		if (Input.isKeyDown(GLFW_KEY_DOWN))
			position.y -= speed;
		if (Input.isKeyDown(GLFW_KEY_LEFT))
			position.x -= speed;
		if (Input.isKeyDown(GLFW_KEY_RIGHT))
			position.x += speed;

		position.y += jump;

		// int cordX = (int) (getX());
		// int cordY = (int) (getY());
		int cordX = (int) (((getCartX() * -1) + 32) / 32);
		int cordY = (int) (((getCartY() * -1) + 32) / 32);
		int mapIndex = 1;
		if (cordX < 8 && cordX < 8) {
			mapIndex = map[cordX][cordY];
		}
		if (mapIndex == 1) {
			position.x = lastX;
			position.y = lastY;
		}
		System.out.println(mapIndex + " x: " + cordX + " y: " + cordY);

		// rot = -delta * 90.0f;
	}

	public void render() {
		Shader.BIRD.enable();

		Vector3f rotPos = position.rotate(new Vector3f(0f, 0f, 1f), 45);
		// rotPos = rotPos.rotate(new Vector3f(1f,0f,0f), -60)
		Matrix4f ml_matrix = Matrix4f.translate(new Vector3f(getIsoX(), getIsoY(), 0));
		// ml_matrix = Matrix4f.translate(getPos(45));

		// ml_matrix = ml_matrix.multiply(Matrix4f.rotateZ(-45));
		// ml_matrix = ml_matrix.multiply(Matrix4f.rotateX(-60));
		Shader.BIRD.setUniformMat4f("ml_matrix", ml_matrix);
		texture.bind();
		mesh.render();
		Shader.BIRD.disable();
	}

	public float getIsoY() {
		float isoY = (position.x + position.y) / 2;
		return isoY;
	}

	public float getIsoX() {
		float isoX = position.x - position.y;
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
		return position.y;
	}

	public float getX() {

		return position.x;
	}

	public float getSize() {
		return width;
	}

}
