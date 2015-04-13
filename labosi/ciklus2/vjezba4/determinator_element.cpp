//
// Created by ivan on 4/13/15.
//

#include "determinator_element.h"

double DeterminatorElement::getPercentile(const Sequence &sequence, int percentile) const {
    size_t np = (size_t) (percentile / 100.0 * sequence.size() + 0.5);
    return sequence.at(np < 0 ? 0 : np);
}