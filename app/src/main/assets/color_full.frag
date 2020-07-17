#version 300 es
//precision mediump float;

in vec3 ourColor;//变量名、类型必须和顶点着色器中out变量一致

out vec4 color;// 片段着色器输出的变量名可以任意命名，类型必须是vec4

void main() {
    color = vec4(ourColor, 1.0f);
}