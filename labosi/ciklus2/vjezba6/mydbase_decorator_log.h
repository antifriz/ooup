//
// Created by ivan on 4/14/15.
//

#ifndef PROJECT_MYDBASE_DECORATOR_LOG_H
#define PROJECT_MYDBASE_DECORATOR_LOG_H

#include <fstream>
#include "mydbase_decorator.h"

class MyDBaseDecoratorLog : public MyDBaseDecorator {
public:
    MyDBaseDecoratorLog(MyDBase &worker) : MyDBaseDecorator(worker) { }

    virtual int query(const Param &p) {
        std::ofstream file;
        file.open("mybase.log", std::ios_base::app | std::ios_base::out);
        file << "query: " << p.column << " " << p.key << " " << p.table << std::endl;
        file.close();
        return worker.query(p);
    }
};


#endif //PROJECT_MYDBASE_DECORATOR_LOG_H
