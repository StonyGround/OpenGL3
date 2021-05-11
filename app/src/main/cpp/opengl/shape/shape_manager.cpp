//
// Created by cl on 2020/7/16.
//

#include "shape_manager.h"
#include "triangle.h"
#include "rectangle.h"
#include "color_full.h"
#include "../texture/texture2d.h"

IOpenGL *ShapeManager::create(int type) {
    IOpenGL *shape = nullptr;
    switch (type) {
        case S_TRIANGLE:
            shape = new Triangle();
            break;
        case S_RECTANGLE:
            shape = new Rectangle();
            break;
        case S_COLOR_FULL:
            shape = new RectangleColorFull();
            break;
        case S_TEXTURE_2D:
            shape = new Texture2D();
            break;
    }
    return shape;
}

void ShapeManager::destroy(IOpenGL *shapePtr) {
    delete shapePtr;
}