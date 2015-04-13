//
// Created by ivan on 4/14/15.
//

#ifndef PROJECT_MYDBASE_H
#define PROJECT_MYDBASE_H

#include <string>

class MyDBase {
public:
    struct Param {
        std::string table;
        std::string column;
        std::string key;
    };

    virtual int query(const Param &p) = 0;
};

#endif //PROJECT_MYDBASE_H
