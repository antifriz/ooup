#include <iostream>
#include <assert.h>
#include "point.h"

struct Point {
    int x;
    int y;
};
struct Shape {
    enum EType { circle, square, rhomb };
    EType type_;
};
struct Circle {
    Shape::EType type_;
    double radius_;
    Point center_;
};
struct Square {
    Shape::EType type_;
    double radius_;
    Point center_;
};
struct Rhomb {
    Shape::EType type_;
    double radius_;
    Point center_;
    double angle;
    double rotation;
};

void drawSquare(struct Square *) {
    std::cerr << "in drawSquare\n";
}

void drawCircle(struct Circle *) {
    std::cerr << "in drawCircle\n";
}

void drawRhomb(struct Rhomb *) {
    std::cerr << "in drawRhomb\n";
}

void drawShapes(Shape **shapes, int n) {
    for (int i = 0; i < n; ++i) {
        struct Shape *s = shapes[i];
        switch (s->type_) {
            case Shape::square:
                drawSquare((struct Square *) s);
                break;
            case Shape::circle:
                drawCircle((struct Circle *) s);
                break;
            case Shape::rhomb:
                drawRhomb((struct Rhomb *) s);
                break;
            default:
                assert(0);
                exit(0);
        }
    }
}

void moveSquare(struct Square *s, int transX, int transY) {
    s->center_.x += transX;
    s->center_.y += transY;
}

void moveCircle(struct Circle *s, int transX, int transY) {
    s->center_.x += transX;
    s->center_.y += transY;
}

void moveRhomb(struct Rhomb *s, int transX, int transY) {
    s->center_.x += transX;
    s->center_.y += transY;
}

void moveShapes(Shape **shapes, int n, int transX, int transY) {
    for (int i = 0; i < n; ++i) {
        struct Shape *s = shapes[i];
        switch (s->type_) {
            case Shape::square:
                moveSquare((struct Square *) s, transX, transY);
                break;
            case Shape::circle:
                moveCircle((struct Circle *) s, transX, transY);
                break;
            case Shape::rhomb:
                moveRhomb((struct Rhomb *) s, transX, transY);
                break;
            default:
                assert(0);
                exit(0);
        }
    }
}

int main() {
    Shape *shapes[4];
    shapes[0] = (Shape *) new Circle;
    shapes[0]->type_ = Shape::circle;
    shapes[1] = (Shape *) new Square;
    shapes[1]->type_ = Shape::square;
    shapes[2] = (Shape *) new Square;
    shapes[2]->type_ = Shape::square;
    shapes[3] = (Shape *) new Circle;
    shapes[3]->type_ = Shape::circle;
    shapes[3] = (Shape *) new Rhomb;
    shapes[3]->type_ = Shape::rhomb;

    drawShapes(shapes, 5);

    moveShapes(shapes, 5, 3, 4);
}