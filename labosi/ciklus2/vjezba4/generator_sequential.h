//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_GENERATOR_SEQUENTIAL_H
#define PROJECT_GENERATOR_SEQUENTIAL_H


#include "determinator.h"
#include "generator.h"

class GeneratorSequential : public Generator {
public:
    GeneratorSequential(int bottom, int top, int step);
};


#endif //PROJECT_GENERATOR_SEQUENTIAL_H
