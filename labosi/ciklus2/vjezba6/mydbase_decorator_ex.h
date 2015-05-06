//
// Created by ivan on 4/14/15.
//

#ifndef PROJECT_MYDBASE_DECORATOR_EX_H
#define PROJECT_MYDBASE_DECORATOR_EX_H

#include "mydbase_decorator.h"

class MyDBaseDecoratorEx : public MyDBaseDecorator {
public:

    MyDBaseDecoratorEx(MyDBase &component) : MyDBaseDecorator(component) { }

    virtual int query(const Param &p) {
        int status = component.query(p);
        if (status < 0) throw status;
        return status;
    }
};


#endif //PROJECT_MYDBASE_DECORATOR_EX_H
