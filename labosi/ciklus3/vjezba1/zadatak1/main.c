//
// Created by ivan on 5/28/15.
//

#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>

#define IDX_FUN_NAME 0
#define IDX_FUN_GREET 1
#define IDX_FUN_MENU 2

typedef char const *(*PTRFUN)();
struct Animal{
    PTRFUN* vtable;
    // vtable entries:
    // 0: char const* name(void* this);
    // 1: char const* greet();
    // 2: char const* menu();
};
// parrots and tigers defined in respective dynamic libraries

// animalPrintGreeting and animalPrintMenu similar as in lab 1

void *myfactory(char const *libname, char const *ctorarg);

void animalPrintGreeting(struct Animal *animalPtr) {
    printf("%s pozdravlja: %s\n",(*animalPtr).vtable[IDX_FUN_NAME](animalPtr), (*animalPtr).vtable[IDX_FUN_GREET]());
}

void animalPrintMenu(struct Animal *animalPtr) {
    printf("%s voli %s\n", (*animalPtr).vtable[IDX_FUN_NAME](), (*animalPtr).vtable[IDX_FUN_MENU]());
}

int main(void){
    struct Animal* p1=(struct Animal*)myfactory("parrot", "Modrobradi");
    struct Animal* p2=(struct Animal*)myfactory("tiger", "Straško");
    if (!p1/* || !p2*/){
        printf("Creation of plug-in objects failed.\n");
        exit(1);
    }

    animalPrintGreeting(p1);//"Sto mu gromova!"
    animalPrintGreeting(p2);//"Mijau!"

    animalPrintMenu(p1);//"brazilske orahe"
    animalPrintMenu(p2);//"mlako mlijeko"

    free(p1); free(p2);
    return 0;
}
