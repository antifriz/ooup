//
// Created by ivan on 4/13/15.
//
#include "distribution_tester.h"
#include "generator_sequential.h"
#include "determinator_element.h"
#include "determinator_interpolated.h"
#include "generator_random.h"
#include "generator_fibonacci.h"

void testSequentialElement() {
    DistributionTester tester(GeneratorSequential(0, 10000, 1), DeterminatorElement());
    tester.printPercentiles();
}

void testSequentialInterpolated() {
    DistributionTester tester(GeneratorSequential(0, 10000, 1), DeterminatorInterpolated());
    tester.printPercentiles();
}

void testRandomElement() {
    DistributionTester tester(GeneratorRandom(20, 20, 10000), DeterminatorElement());
    tester.printPercentiles();
}

void testRandomInterpolated() {
    DistributionTester tester(GeneratorRandom(20, 20, 10000), DeterminatorInterpolated());
    tester.printPercentiles();
}

void testFibonacciElement() {
    DistributionTester tester(GeneratorFibonacci(20), DeterminatorElement());
    tester.printPercentiles();
}

void testFibonacciInterpolated() {
    DistributionTester tester(GeneratorFibonacci(20), DeterminatorInterpolated());
    tester.printPercentiles();
}


int main() {
    testSequentialElement();
    testSequentialInterpolated();
    testRandomElement();
    testRandomInterpolated();
    testFibonacciElement();
    testFibonacciInterpolated();
    return 0;
}
