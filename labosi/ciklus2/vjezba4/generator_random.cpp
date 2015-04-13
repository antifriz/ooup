//
// Created by ivan on 4/13/15.
//

#include "generator_random.h"

GeneratorRandom::GeneratorRandom(double mean, double std, int n) {
    std::mt19937 generator;
    std::normal_distribution<double> normal(mean, std);
    for (int i = 0; i < n; ++i)
        sequence.push_back((int) round(normal(generator)));
    sort(sequence.begin(), sequence.end());
}