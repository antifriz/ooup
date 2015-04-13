//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_CLIENT2_HPP
#define PROJECT_CLIENT2_HPP

#include "abstractDatabase.h"


class Client2 {
    AbstractDatabase &myDatabase;
public:
    Client2(AbstractDatabase &db) : myDatabase(db) { }

    void transaction() {
        myDatabase.getData();
    }
};

#endif //PROJECT_CLIENT2_HPP
