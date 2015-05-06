#include <iostream>
#include <vector>

class Point {
private:
    int x;
    int y;
public:
    void translate(int tx, int ty) {
        x += tx;
        y += ty;
    }
};

class Shape {
private:
    Point center_;
    double radius_;
public:
    Shape() { };

    Shape(const Point &center, double radius_) : center_(center), radius_(radius_) { };

    Point const &getCenter() const { return center_; }

    double getRadius() const { return radius_; }

    virtual void draw() const = 0;

    virtual void translate(int x, int y) { center_.translate(x, y); };

    static void drawMultiple(std::vector<Shape *> shapes) { for (auto &shape: shapes) shape->draw(); };

    static void moveMultiple(std::vector<Shape *> shapes, int x, int y) {
        for (auto &shape: shapes)
            shape->translate(x, y);
    };
};

class Circle : public Shape {
public:
    Circle() : Shape() { };

    Circle(Point const &center, double radius_) : Shape(center, radius_) { }

    virtual void draw() const { std::cerr << "in drawCircle\n"; };
};

class Square : public Shape {
public:
    Square() : Shape() { };

    Square(Point const &center, double radius_) : Shape(center, radius_) { }

    virtual void draw() const { std::cerr << "in drawSquare\n"; };
};

class Rhomb : public Shape {
private:
    double angle_;
    double rotation_;
public:
    Rhomb() : Shape() { };

    Rhomb(Point const &center, double radius_, double angle, double rotation) : Shape(center, radius_), angle_(angle),
                                                                                rotation_(rotation) { }

    double getAngle() const { return angle_; }

    double getRotation() const { return rotation_; }

    virtual void draw() const { std::cerr << "in drawRhomb\n"; };
};

int main() {
    using namespace std;

    vector<Shape *> shapes;

    shapes.push_back(new Circle());
    shapes.push_back(new Square());
    shapes.push_back(new Square());
    shapes.push_back(new Circle());
    shapes.push_back(new Rhomb());

    Shape::drawMultiple(shapes);

    Shape::moveMultiple(shapes, 3, 4);
    return 0;
}
