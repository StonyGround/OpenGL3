//
// Created by cl on 2020/7/16.
//

#include "shape_manager.h"
#include "triangle.h"

Shape *ShapeManager::create(int type) {
    Shape *shape = nullptr;
    if (type == S_TRIANGLE) {
        shape = new Triangle();
    }
    return shape;
}

void ShapeManager::destroy(Shape *shapePtr) {
    delete shapePtr;
}