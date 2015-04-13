//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_GENERATOR_RANDOM_H
#define PROJECT_GENERATOR_RANDOM_H

#include <random>
#include <algorithm>
#include "generator.h"

class GeneratorRandom : public Generator {
public:
    GeneratorRandom(double mean, double std, int n);
};

#endif //PROJECT_GENERATOR_RANDOM_H
