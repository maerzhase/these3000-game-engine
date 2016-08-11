package com.these3000.core.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback {

	public static boolean[] _keys = new boolean[65536];

	public void invoke(long window, int key, int scancode, int action, int mods) {
		_keys[key] = action != GLFW.GLFW_RELEASE;
	}

	public static boolean isKeyDown(int keycode) {
		return _keys[keycode];
	}

}
