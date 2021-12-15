package dev.paulshields.aoc.common

fun <T> MutableMap<T, Int>.addToValue(key: T, addition: Int) {
    this[key] = this[key]?.plus(addition) ?: addition
}

fun <T> MutableMap<T, Long>.addToValue(key: T, addition: Long) {
    this[key] = this[key]?.plus(addition) ?: addition
}

fun <T> MutableMap<T, Int>.subtractFromValue(key: T, addition: Int) {
    this[key] = this[key]?.plus(addition * -1) ?: (addition * -1)
}

fun <T> MutableMap<T, Long>.subtractFromValue(key: T, addition: Long) {
    this[key] = this[key]?.plus(addition * -1) ?: (addition * -1)
}

fun <T> MutableMap<T, Int>.incrementValue(key: T) {
    this[key] = this[key]?.plus(1) ?: 1
}

@JvmName("incrementValueTLong")
fun <T> MutableMap<T, Long>.incrementValue(key: T) {
    this[key] = this[key]?.plus(1) ?: 1
}

fun <T> MutableMap<T, Int>.decrementValue(key: T) {
    this[key] = this[key]?.plus(-1) ?: -1
}

@JvmName("decrementValueTLong")
fun <T> MutableMap<T, Long>.decrementValue(key: T) {
    this[key] = this[key]?.plus(-1) ?: -1
}
