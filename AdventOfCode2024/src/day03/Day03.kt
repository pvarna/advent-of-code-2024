package day03

import java.io.File

val mulRegex = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")
val dontRegex = Regex("""don't\(\).*?(?:do\(\)|$)""")

fun parseInput(fileName: String): String {
    return File(fileName).bufferedReader().use { it.readText() }
}

fun partOne(input: String): Int {
    return mulRegex
        .findAll(input)
        .sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
}

fun partTwo(input: String): Int {
    return input
        .replace("\n", "|")
        .replace(dontRegex, "")
        .let(::partOne)
}

fun main() {
    val inputs = listOf("src/day03/test.txt", "src/day03/official.txt")
        .map(::parseInput)

    println("Task 1:")
    inputs.forEach { input ->
        println(partOne(input))
    }

    println("Task 2:")
    inputs.forEach { input ->
        println(partTwo(input))
    }
}