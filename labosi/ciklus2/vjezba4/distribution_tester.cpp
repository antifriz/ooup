//
// Created by ivan on 4/13/15.
//

#include "distribution_tester.h"

double DistributionTester::getPercentile(int percentile) const {
    return determinator.getPercentile(sequence, percentile);
}

void DistributionTester::printPercentiles() const {
    for (int i = 10; i < 100; i += 10)
        std::cout << i << ": " << getPercentile(i) << std::endl;
}