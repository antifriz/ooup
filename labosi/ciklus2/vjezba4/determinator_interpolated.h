//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_DETERMINATOR_INTERPOLATED_H
#define PROJECT_DETERMINATOR_INTERPOLATED_H

#include <glob.h>
#include <math.h>
#include "determinator.h"

class DeterminatorInterpolated : public Determinator {
private:
    double percentRank(size_t size, size_t n) const;

public:
    virtual double getPercentile(const Sequence &sequence, int percentile) const;;
};

#endif //PROJECT_DETERMINATOR_INTERPOLATED_H
