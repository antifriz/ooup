//
// Created by ivan on 4/13/15.
//
#include <vector>
#include "generator_sequential.h"

GeneratorSequential::GeneratorSequential(int bottom, int top, int step) {
    for (int i = bottom; i < top; i += step)
        sequence.push_back(i);
}