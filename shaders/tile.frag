#version 330 core

layout (location = 0) out vec4 color;

in DATA
{
	vec2 tc;
	vec3 position;
} fs_in;

uniform vec2 player;
uniform sampler2D tex;

void main()
{
	color = texture(tex, fs_in.tc);
	color *= 4.0 / (length(player - fs_in.position.xy) + 1.5) + 0.7;
	if (color.w < .4)
		discard;
}