//
// Created by ivan on 4/13/15.
//

#include "generator_fibonacci.h"

GeneratorFibonacci::GeneratorFibonacci(int n) {
    for (int i1 = 1, i0 = 1; n > 0; --n, i1 += i0, i0 = i1 - i0)
        sequence.push_back(i0);
}