//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_DETERMINATOR_H
#define PROJECT_DETERMINATOR_H

#include <vector>

typedef std::vector<int> Sequence;


class Determinator {
public:
    virtual double getPercentile(const Sequence &sequence, int percentile) const = 0;
};

#endif //PROJECT_DETERMINATOR_H
