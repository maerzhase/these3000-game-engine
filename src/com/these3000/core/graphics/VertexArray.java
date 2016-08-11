package com.these3000.core.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.these3000.core.utils.BufferUtils;

public class VertexArray {

	private int _vao, _vbo, _ibo, _tbo;
	private int _count;

	public VertexArray(int count) {
		_count = count;
		_vao = glGenVertexArrays();
	}

	public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {
		_count = indices.length;

		_vao = glGenVertexArrays();
		glBindVertexArray(_vao);

		_vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, _vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader._VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader._VERTEX_ATTRIB);

		_tbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, _tbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader._TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader._TCOORD_ATTRIB);

		_ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void bind() {
		glBindVertexArray(_vao);
		if (_ibo > 0)
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, _ibo);
	}

	public void unbind() {
		if (_ibo > 0)
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

		glBindVertexArray(0);
	}

	public void draw() {
		if (_ibo > 0)
			glDrawElements(GL_TRIANGLES, _count, GL_UNSIGNED_BYTE, 0);
		else
			glDrawArrays(GL_TRIANGLES, 0, _count);
	}

	public void render() {
		bind();
		draw();
	}

}
