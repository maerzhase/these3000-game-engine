package com.these3000.core.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.these3000.core.maths.Matrix4f;
import com.these3000.core.maths.Vector3f;
import com.these3000.core.utils.ShaderUtils;

public class Shader {

	public static final int _VERTEX_ATTRIB = 0;
	public static final int _TCOORD_ATTRIB = 1;

	public static Shader _TILE, _PLAYER, _FADE;

	private boolean _enabled = false;

	private final int _ID;
	private Map<String, Integer> _locationCache = new HashMap<String, Integer>();

	public Shader(String vertex, String fragment) {
		_ID = ShaderUtils.load(vertex, fragment);
	}

	public static void loadAll() {
		_TILE = new Shader("shaders/tile.vert", "shaders/tile.frag");
		_PLAYER = new Shader("shaders/player.vert", "shaders/player.frag");
		// FADE = new Shader("shaders/fade.vert", "shaders/fade.frag");
	}

	public int getUniform(String name) {
		if (_locationCache.containsKey(name))
			return _locationCache.get(name);

		int result = glGetUniformLocation(_ID, name);
		if (result == -1)
			System.err.println("Could not find uniform variable '" + name + "'!");
		else
			_locationCache.put(name, result);
		return result;
	}

	public void setUniform1i(String name, int value) {
		if (!_enabled)
			enable();
		glUniform1i(getUniform(name), value);
	}

	public void setUniform1f(String name, float value) {
		if (!_enabled)
			enable();
		glUniform1f(getUniform(name), value);
	}

	public void setUniform2f(String name, float x, float y) {
		if (!_enabled)
			enable();
		glUniform2f(getUniform(name), x, y);
	}

	public void setUniform3f(String name, Vector3f vector) {
		if (!_enabled)
			enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}

	public void setUniformMat4f(String name, Matrix4f matrix) {
		if (!_enabled)
			enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}

	public void enable() {
		glUseProgram(_ID);
		_enabled = true;
	}

	public void disable() {
		glUseProgram(0);
		_enabled = false;
	}

}
