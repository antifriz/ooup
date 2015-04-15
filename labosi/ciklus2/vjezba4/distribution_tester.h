//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_DISTRIBUTION_TESTER_H
#define PROJECT_DISTRIBUTION_TESTER_H

#include "determinator.h"
#include "generator.h"

using namespace std;

class DistributionTester {
private:
    const Generator &generator;
    const Determinator &determinator;
    Sequence const &sequence;
public:
    DistributionTester(const Generator &generator, const Determinator &determinator) : generator(generator),
                                                                                       determinator(determinator),
                                                                                       sequence(
                                                                                               generator.getSequence()) {
    }

    double getPercentile(int percentile) const;

    void printPercentiles() const;
};


#endif //PROJECT_DISTRIBUTION_TESTER_H
