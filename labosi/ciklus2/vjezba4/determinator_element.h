//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_DETERMINATOR_ELEMENT_H
#define PROJECT_DETERMINATOR_ELEMENT_H

#include "determinator.h"

class DeterminatorElement : public Determinator {
public:
    virtual double getPercentile(const Sequence &sequence, int percentile) const;;
};

#endif //PROJECT_DETERMINATOR_ELEMENT_H
