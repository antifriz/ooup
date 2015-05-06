//
// Created by ivan on 4/13/15.
//
#include <iostream>
#include "mydbase.h"
#include "mydbase_decorator_ex.h"
#include "mydbase_decorator_log.h"
#include "mydbase_concrete.h"

int main() {
    srand((unsigned int) time(nullptr));

    MyDBase::Param param;
    param.key = "bla";
    param.column = "blah";
    param.table = "blahblah";


    MyDBaseConcrete raw;

    //std::cout<<raw.query(param)<<std::endl;


    MyDBaseDecoratorEx ex(raw);

    //std::cout<<ex.query(param)<<std::endl;


    MyDBaseDecoratorLog log(raw);
    std::cout << log.query(param) << std::endl;


    MyDBaseDecoratorEx ult(log);
    //std::cout<<ult.query(param)<<std::endl;


    return 0;
}

