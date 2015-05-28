//
// Created by ivan on 5/28/15.
//

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dlfcn.h>
#include <error.h>
#include "myfactory.h"


void *myfactory(char const *libname, char const *ctorarg) {
    char lib_path[FILENAME_MAX];
    void *handle;
    void *(*create_fun_ptr)(const char *);
    lib_path[0]='\0';
    strcat(lib_path, "./");
    strcat(lib_path, libname);
    strcat(lib_path, ".so");

    handle = dlopen(lib_path, RTLD_NOW);
    if (!handle) {
        puts(lib_path);
        fprintf(stderr, "%s\n", dlerror());
        return NULL;
    }

    dlerror();


    *(void **) (&create_fun_ptr) = dlsym(handle, "create");

    return (*create_fun_ptr)(ctorarg);
}