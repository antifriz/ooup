//
// Created by Ivan on 21-Mar-15.
//

#include "Square.h"

/*private final*/double _Square__value_at(Unary_Function *obj_ptr, double x) {
    return x * x;
}

/*private final*/void _Square__Delete(Unary_Function *obj_ptr) {
}

FUNCPTR _Square__function_table[3] = {_Square__Delete, (FUNCPTR) _Square__value_at, (FUNCPTR) _Unary_Function__negative_value_at};

/*constructor*/Unary_Function *Square__Create(int lb, int ub) {
    Unary_Function *newUF = Unary_Function__Create(lb, ub);
    newUF->function_table = _Square__function_table;
    return newUF;
}


