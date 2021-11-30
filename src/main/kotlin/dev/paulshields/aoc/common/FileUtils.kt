package dev.paulshields.aoc.common

private val anyObject = object { }

fun readFileAsString(filePath: String) = anyObject
    .javaClass
    .getResource(filePath)
    ?.readText()
    ?: ""

fun readFileAsStringList(filePath: String) = readFileAsString(filePath)
    .lines()
    .filter { it.isNotEmpty() }
