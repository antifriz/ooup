//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_CLIENT1_HPP
#define PROJECT_CLIENT1_HPP

#include "mockDatabase.h"

class Client1 {
    MockDatabase myDatabase;
    public:
    void transaction() {
        myDatabase.getData();
    }
};

#endif //PROJECT_CLIENT1_HPP
