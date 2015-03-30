//
// Created by Ivan on 21-Mar-15.
//

#include <malloc.h>
#include "Linear.h"

typedef struct {
    double a;
    double b;
} _Linear;

/*private final*/void _Linear__Delete(Unary_Function *obj_ptr) {
    free(obj_ptr->data);
}

/*private final*/double _Linear__value_at(Unary_Function *obj_ptr, double x) {
    _Linear *der_ptr = (_Linear *) obj_ptr->data;
    return der_ptr->a * x + der_ptr->b;
}

FUNCPTR _Linear__function_table[3] = {_Linear__Delete, (FUNCPTR) _Linear__value_at, (FUNCPTR) _Unary_Function__negative_value_at};

/*constructor*/Unary_Function *Linear__Create(int lb, int ub, double a_coef, double b_coef) {
    Unary_Function *newUF = Unary_Function__Create(lb, ub);
    newUF->function_table = _Linear__function_table;

    _Linear *newL = (_Linear *) malloc(sizeof(_Linear));
    newL->a = a_coef;
    newL->b = b_coef;
    newUF->data = (void *) newL;

    return newUF;
}

