project(c3v1z1)

file(GLOB SOURCE_FILES
main.c
myfactory.c
)

add_executable(${PROJECT_NAME} ${SOURCE_FILES})
target_link_libraries(${PROJECT_NAME} dl)


foreach(animal tiger parrot)
    add_library(${animal} SHARED ${animal}.c)
endforeach()