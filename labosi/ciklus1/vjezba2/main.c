//
// Created by Ivan on 21-Mar-15.
//

#include <stdio.h>
#include "Square.h"
#include "Linear.h"

int main() {
    Unary_Function *f1 = Square__Create(-2, 2);
    Unary_Function__tabulate(f1);
    Unary_Function *f2 = Linear__Create(-2, 2, 5, -2);
    Unary_Function__tabulate(f2);
    printf("f1==f2: %s\n", Unary_Function__same_functions_for_ints__STATIC(f1, f2, 1E-6) ? "DA" : "NE");
    printf("neg_val f2(1) = %lf\n", Unary_Function__negative_value_at(f2, 1.0));
    Unary_Function__Delete(f1);
    Unary_Function__Delete(f2);
    return 0;
}