#version 300 es
layout(location=0) in vec3 vPosition;
layout (location = 1) in vec3 color;

out vec3 ourColor;// 向片段着色器输出一个颜色

void main() {
    gl_Position = vec4(vPosition, 1.0);
    ourColor=color;
}
