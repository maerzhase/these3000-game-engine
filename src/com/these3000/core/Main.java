package com.these3000.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.these3000.core.graphics.Shader;
import com.these3000.core.input.Input;
import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;
import com.these3000.core.scenes.Scene1;

public class Main {

	private int width = 1024;
	private int height = 768;

	private boolean running = false;

	private long window;

	private Scene1 scene;

	public void start() {
		running = true;
		this.init();

	}

	private void init() {

		if (!glfwInit()) {
			System.err.println("Could not initialize GLFW!");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);

		window = glfwCreateWindow(width, height, "These3000", NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);

		glfwSetKeyCallback(window, new Input());

		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();

		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();

		float mWidth = width / 2;
		float mHeight = height / 2;
		float x1 = -mWidth;
		float x2 = mWidth;
		float y1 = -mHeight;// * 9.0f / 16.0f;
		float y2 = mHeight;// * 9.0f / 16.0f;
		float z1 = -mWidth * 2;
		float z2 = mWidth * 2;

		Matrix4f pr_matrix = Matrix4f.orthographic(x1, x2, y1, y2, z1, z2);
		// pr_matrix = pr_matrix.multiply(Matrix4f.translate(new Vector3f(5, 5, 0)));
		// pr_matrix = pr_matrix.multiply(Matrix4f.translate(new Vector3f(-5, -5, 0)));
		// pr_matrix = pr_matrix.multiply(Matrix4f.rotateX(60));
		// pr_matrix = pr_matrix.multiply(Matrix4f.rotateZ(45));
		// pr_matrix = pr_matrix.multiply(Matrix4f.translate(new Vector3f(5, 5, 0)));

		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BG.setUniform1i("tex", 1);
		Shader.BIRD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.BIRD.setUniform1i("tex", 1);
		scene = new Scene1();
		run();

	}

	public void run() {
		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window))
				running = false;
		}

		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void update() {
		glfwPollEvents();
		scene.update();
	}

	private void render() {
		// glClearColor(1f, 1f, 1f, 1f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		scene.render();

		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println(error);

		glfwSwapBuffers(window);
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
