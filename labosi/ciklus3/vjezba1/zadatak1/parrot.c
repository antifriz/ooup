//
// Created by ivan on 5/28/15.
//

#include <stdlib.h>
#include <string.h>


typedef char const *(*PTRFUN)();

struct Parrot {
    PTRFUN *vtable;
    char *name;
};

char const *name_fun(void *this) {
    return ((struct Parrot *) this)->name;
}

char const *greet_fun() {
    return "Sto mu gromova!";
}

char const *menu_fun() {
    return "brazilske orahe";
}


void *create(char const *name) {
    struct Parrot *animal = (struct Parrot *) malloc(sizeof(struct Parrot));

    animal->vtable = malloc(sizeof(PTRFUN) * 3);
    animal->vtable[0] = name_fun;
    animal->vtable[1] = greet_fun;
    animal->vtable[2] = menu_fun;

    size_t size = strlen(name) + 1;
    animal->name = malloc(size);
    memcpy(animal->name, name, size);

    return animal;
}
