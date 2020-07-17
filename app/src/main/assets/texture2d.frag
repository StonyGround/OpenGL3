#version 300 es

in vec3 ourColor;//变量名、类型必须和顶点着色器中out变量一致
in vec2 TexCoord;

out vec4 color;// 片段着色器输出的变量名可以任意命名，类型必须是vec4

uniform sampler2D ourTexture;

void main() {
    color = texture(ourTexture, TexCoord);
}