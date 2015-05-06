//
// Created by ivan on 4/14/15.
//

#ifndef PROJECT_MYDBASE_RAW_H
#define PROJECT_MYDBASE_RAW_H

#include "mydbase.h"

class MyDBaseConcrete : public MyDBase {
public:
    virtual int query(const Param &p) {
        auto r = ((double) rand() / (RAND_MAX));
        return r > 0.8 ? -(int) (r * 10) : 0;
    }
};


#endif //PROJECT_MYDBASE_RAW_H
