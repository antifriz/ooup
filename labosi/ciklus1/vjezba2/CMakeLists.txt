cmake_minimum_required(VERSION 2.8)
project(c1v2)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS}")
set(CMAKE_CXX_FLAGS "-std=c++11 -Wall -g")

add_executable(${PROJECT_NAME}.cpp_u_c main.c Unary_Function.c Linear.c Square.c)
add_executable(${PROJECT_NAME}.izvorni_program izvorni_program.cpp)