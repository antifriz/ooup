//
// Created by ivan on 4/13/15.
//
#include <vector>
#include <ostream>
#include <math.h>
#include "determinator_interpolated.h"

double DeterminatorInterpolated::getPercentile(const Sequence &sequence, int percentile) const {
    size_t size = sequence.size();
    double coeff = 100.0 / size;

    double calcIdx = percentile / coeff - .5;

    if (calcIdx <= 0) return sequence.front();
    if (calcIdx >= size - 1) return sequence.back();

    size_t idx0 = (size_t) floor(calcIdx);
    int v0 = sequence.at(idx0);
    int v1 = sequence.at(idx0 + 1);

    double p0 = 100.0 / size * (idx0);

    double slope = size / 100.0 * (percentile - p0);

    return v0 + (v1 - v0) * slope;
}

double DeterminatorInterpolated::percentRank(size_t size, size_t n) const {
    return (100.0 / size * (n + .5));
}