#version 300 es
precision mediump float;
uniform vec4 vColor;
out vec4 outColor;// 片段着色器输出的变量名可以任意命名，类型必须是vec4
void main() {
    outColor = vColor;
}