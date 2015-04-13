//
// Created by ivan on 4/13/15.
//

#include "client2.hpp"
#include "mockDatabase.h"

void assertGetDataWasCalled(MockDatabase database);

int main() {
    MockDatabase *pdb = new MockDatabase();

    Client2 client(*pdb);

    client.transaction();
    assertGetDataWasCalled(*pdb);
    return 0;
}

void assertGetDataWasCalled(MockDatabase database) {
    //do sth
}