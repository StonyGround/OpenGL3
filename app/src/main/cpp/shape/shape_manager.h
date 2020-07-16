//
// Created by cl on 2020/7/16.
//

#ifndef MY_APPLICATION_SHAPE_MANAGER_H
#define MY_APPLICATION_SHAPE_MANAGER_H

// circle_manager.h
#include "shape.h"

#define S_TRIANGLE 1
#define S_RECTANGLE 2

// 圆的创建工厂类
class ShapeManager {
public:

    static Shape *create(int type);     // 创建实例

    static void destroy(Shape *shapePtr);   // 销毁实例
};

#endif //MY_APPLICATION_SHAPE_MANAGER_H
