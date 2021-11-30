package dev.paulshields.aoc.testcommon

import org.junit.jupiter.api.DynamicTest

fun <T> parameterizedTest(vararg elements: T) = elements.toList()

fun <T> List<T>.runTest(test: (T) -> Unit) = this.map { parameters ->
    val testName = getNameOfTheParameterizedTestMethod()

    DynamicTest.dynamicTest("$testName : $parameters") {
        test(parameters)
    }
}

fun getNameOfTheParameterizedTestMethod(): String {
    return try {
        Thread.currentThread().stackTrace[3].methodName
    } catch (e: Exception) {
        ""
    }
}
