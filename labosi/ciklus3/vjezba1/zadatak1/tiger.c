//
// Created by ivan on 5/28/15.
//


#include <stdlib.h>
#include <string.h>

typedef char const *(*PTRFUN)();

struct Tiger {
    PTRFUN *vtable;
    char *name;
};

char const *name_fun(void *this) {
    return ((struct Tiger *) this)->name;
}

char const *greet_fun() {
    return "Mijau!";
}

char const *menu_fun() {
    return "mlako mlijeko";
}


void *create(char const *name) {
    struct Tiger *animal = (struct Tiger *) malloc(sizeof(struct Tiger));

    animal->vtable = malloc(sizeof(PTRFUN) * 3);
    animal->vtable[0] = name_fun;
    animal->vtable[1] = greet_fun;
    animal->vtable[2] = menu_fun;

    size_t size = strlen(name) + 1;
    animal->name = malloc(size);
    memcpy(animal->name, name, size);

    return animal;
}
