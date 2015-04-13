//
// Created by ivan on 4/14/15.
//

#ifndef PROJECT_MYDBASE_DECORATOR_H
#define PROJECT_MYDBASE_DECORATOR_H

#include "mydbase.h"

class MyDBaseDecorator : public MyDBase {
protected:
    MyDBase &worker;
public:
    MyDBaseDecorator(MyDBase &worker) : worker(worker) { }
};


#endif //PROJECT_MYDBASE_DECORATOR_H
