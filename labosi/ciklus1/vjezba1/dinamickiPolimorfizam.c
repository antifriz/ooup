//
// Created by Ivan on 21-Mar-15.
//

#include <stdlib.h>
#include <stdio.h>

typedef char const *(*PTRFUN)();

struct Animal {
    PTRFUN *functionTable;
    char *animalName;
};

char const *dogGreet(void) {
    return "vau!";
}

char const *dogMenu(void) {
    return "kuhanu govedinu";
}

char const *catGreet(void) {
    return "mijau!";
}

char const *catMenu(void) {
    return "konzerviranu tunjevinu";
}

#define IDX_FUN_GREET 0
#define IDX_FUN_MENU 1
#define IDX_FUN_CNT 2

PTRFUN dogFuncts[IDX_FUN_CNT] = {dogGreet, dogMenu};
PTRFUN catFuncts[IDX_FUN_CNT] = {catGreet, catMenu};


void animalPrintGreeting(struct Animal *animalPtr) {
    printf("%s pozdravlja: %s\n", animalPtr->animalName, (*animalPtr).functionTable[IDX_FUN_GREET]());
}

void animalPrintMenu(struct Animal *animalPtr) {
    printf("%s voli %s\n", animalPtr->animalName, (*animalPtr).functionTable[IDX_FUN_MENU]());
}

struct Animal *createAnimal(char *animalName) {
    struct Animal *animalPtr = (struct Animal *) malloc(sizeof(struct Animal));
    animalPtr->animalName = animalName;
    return animalPtr;
}

struct Animal *createDog(char *animalName) {
    struct Animal *animal = createAnimal(animalName);
    animal->functionTable = dogFuncts;
    return animal;
}

struct Animal *createCat(char *animalName) {
    struct Animal *animal = createAnimal(animalName);
    animal->functionTable = catFuncts;
    return animal;
}

void testAnimals(void) {
    struct Animal *p1 = createDog("Hamlet");
    struct Animal *p2 = createCat("Ofelija");
    struct Animal *p3 = createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1);
    free(p2);
    free(p3);
}

int main() {
    testAnimals();
    return 0;
}

