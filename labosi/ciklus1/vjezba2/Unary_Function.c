//
// Created by Ivan on 21-Mar-15.
//

#include "Unary_Function.h"

#include <stdio.h>
#include <malloc.h>


FUNCPTR _Unary_Function__function_table[3] = {NULL, NULL, (FUNCPTR) _Unary_Function__negative_value_at};

/*constructor*/Unary_Function *Unary_Function__Create(int lb, int ub) {
    Unary_Function *newUF = (Unary_Function *) malloc(sizeof(Unary_Function));
    newUF->function_table = _Unary_Function__function_table;
    newUF->lower_bound = lb;
    newUF->upper_bound = ub;
    newUF->data = NULL;
    return newUF;
}

/*virtual destructor*/void Unary_Function__Delete(Unary_Function *obj_ptr) {
    (((void (*)(Unary_Function *)) (obj_ptr->function_table[_UNARY_FUNCTION__FUN_IDX__DELETE])))(obj_ptr);
    free(obj_ptr);
}

/*virtual*/double Unary_Function__value_at(Unary_Function *obj_ptr, double x) {
    return (((double (*)(Unary_Function *, double)) (obj_ptr->function_table[_UNARY_FUNCTION__FUN_IDX__VALUE_AT])))(obj_ptr, x);
}

/*virtual*/double Unary_Function__negative_value_at(Unary_Function *obj_ptr, double x) {
    return (((double (*)(Unary_Function *, double)) (obj_ptr->function_table[_UNARY_FUNCTION__FUN_IDX__NEGATIVE_VALUE_AT])))(obj_ptr, x);
}

/*final*/double _Unary_Function__negative_value_at(Unary_Function *obj_ptr, double x) {
    return -Unary_Function__value_at(obj_ptr, x);
}

/*final*/void Unary_Function__tabulate(Unary_Function *obj_ptr) {
    int x;
    for (x = obj_ptr->lower_bound; x <= obj_ptr->upper_bound; x++) {
        printf("f(%d)=%lf\n", x, Unary_Function__value_at(obj_ptr, x));
    }
}

/*static*/int Unary_Function__same_functions_for_ints__STATIC(Unary_Function *f1, Unary_Function *f2, double tolerance) {
    int x;
    if (f1->lower_bound != f2->lower_bound) return 0;
    if (f1->upper_bound != f2->upper_bound) return 0;
    for (x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = Unary_Function__value_at(f1, x) - Unary_Function__value_at(f2, x);
        if (delta < 0) delta = -delta;
        if (delta > tolerance) return 0;
    }
    return 1;
}
