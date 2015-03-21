//
// Created by Ivan on 21-Mar-15.
//

#ifndef _UNARY_FUNCTION_H_
#define _UNARY_FUNCTION_H_

typedef void (*FUNCPTR)();

typedef struct {
    FUNCPTR *function_table;
    int lower_bound;
    int upper_bound;
    void *data;
} Unary_Function;

/*constructor*/Unary_Function *Unary_Function__Create(int lb, int ub);

#define _UNARY_FUNCTION__FUN_IDX__DELETE 1

/*virtual destructor*/void Unary_Function__Delete(Unary_Function *obj_ptr);

#define _UNARY_FUNCTION__FUN_IDX__VALUE_AT 1

/*virtual*/double Unary_Function__value_at(Unary_Function *obj_ptr, double x);

#define _UNARY_FUNCTION__FUN_IDX__NEGATIVE_VALUE_AT 2

/*virtual*/double Unary_Function__negative_value_at(Unary_Function *obj_ptr, double x);

/*final*/double _Unary_Function__negative_value_at(Unary_Function *obj_ptr, double x);

/*final*/void Unary_Function__tabulate(Unary_Function *obj_ptr);

/*static*/int Unary_Function__same_functions_for_ints__STATIC(Unary_Function *f1, Unary_Function *f2, double tolerance);

#endif //_UNARY_FUNCTION_H_
