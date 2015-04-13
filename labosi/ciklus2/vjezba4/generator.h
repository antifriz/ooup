//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_GENERATOR_H
#define PROJECT_GENERATOR_H

#include <vector>

typedef std::vector<int> Sequence;

class Generator {
protected:
    Sequence sequence;
public:
    const Sequence &getSequence() const { return sequence; }
};

#endif //PROJECT_GENERATOR_H
