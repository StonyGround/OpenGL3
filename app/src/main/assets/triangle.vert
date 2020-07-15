//#version 300 es
attribute vec4 vPosition;
varying vec4 vCorlor;
attribute vec4 aColor;
void main() {
    gl_Position = vPosition;
    vCorlor=aColor;
}
