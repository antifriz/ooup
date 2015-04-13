//
// Created by ivan on 4/13/15.
//
#include <vector>
#include "determinator_interpolated.h"

double DeterminatorInterpolated::getPercentile(const Sequence &sequence, int percentile) const {

/*
    std::vector<double> percentils;

    double previous = 0;
    double next = 0;
    double vk0 = 0;

    double vk1 = 0;


    for (size_t i = 0; i < sequence.size(); ++i) {
        percentils.push_back(100 * (i - 0.5) / sequence.size());
        if (percentils.size() > 1 && percentile >= percentils.at(percentils.size() - 2) &&
            percentile < percentils.at(percentils.size() - 1)) {
            previous = percentils.at(percentils.size() - 2);
            next = percentils.at(percentils.size() - 1);
            vk0 = sequence.at(i - 1);
            vk1 = sequence.at(i);
        }
    }

    if (percentile <= percentils.front()) return sequence.front();
    if (percentile >= percentils.back()) return sequence.back();

    return vk0 + (percentile - previous) * (vk1 - vk0) / (next - previous);
*/

    size_t size = sequence.size();
    double coeff = 100.0 / size;

    double calcIdx = percentile / coeff - .5;

    if (calcIdx <= 0) return sequence.front();
    if (calcIdx >= size - 1) return sequence.back();

    size_t idx0 = (size_t) floor(calcIdx);
    int v0 = sequence.at(idx0);
    int v1 = sequence.at(idx0 + 1);

    double p0 = 100 / size * (idx0 + .5);

    double slope = size / 100.0 * (percentile - p0);

    return v0 + (v1 - v0) * slope;
}

double DeterminatorInterpolated::percentRank(size_t size, size_t n) const {
    return (100.0 / size * (n + .5));
}