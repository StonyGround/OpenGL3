#version 300 es
layout(location=0) in vec3 vPosition;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 textCoord;

out vec3 ourColor;// 向片段着色器输出一个颜色
out vec2 TexCoord;

void main() {
    gl_Position = vec4(vPosition, 1.0);
    ourColor=color;
    TexCoord=textCoord;
}
