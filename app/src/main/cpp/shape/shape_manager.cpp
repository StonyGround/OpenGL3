//
// Created by cl on 2020/7/16.
//

#include "shape_manager.h"
#include "triangle.h"
#include "rectangle.h"

Shape *ShapeManager::create(int type) {
    Shape *shape = nullptr;
    switch (type) {
        case S_TRIANGLE:
            shape = new Triangle();
            break;
        case S_RECTANGLE:
            shape = new Rectangle();
            break;
    }
    return shape;
}

void ShapeManager::destroy(Shape *shapePtr) {
    delete shapePtr;
}