//
// Created by ivan on 4/13/15.
//

#ifndef PROJECT_MOCKDATABASE_H
#define PROJECT_MOCKDATABASE_H

#include "abstractDatabase.h"

class MockDatabase : public AbstractDatabase {
public:
    virtual void getData() { }
};

#endif //PROJECT_MOCKDATABASE_H
