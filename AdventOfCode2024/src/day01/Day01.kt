package day01

import java.io.File
import kotlin.math.abs

fun parseInput(fileName: String): Pair<List<Int>, List<Int>> {
    val lines = File(fileName).readLines()

    val linesAsListOfNumbers = lines.map { it.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }
    val list1 = linesAsListOfNumbers.map { it[0] }
    val list2 = linesAsListOfNumbers.map { it[1] }

    return Pair(list1, list2)
}

fun getTotalDistance(list1: List<Int>, list2: List<Int>): Int {
    val sortedList1 = list1.sorted()
    val sortedList2 = list2.sorted()

    return sortedList1.zip(sortedList2).sumOf { abs(it.first - it.second) }
}

fun occurrencesIn(number: Int, list: List<Int>): Int {
    return list.count { it == number }
}

fun getSimilarityScore(list1: List<Int>, list2: List<Int>): Int {
    return list1.map { it * occurrencesIn(it, list2) }.sum()
}

fun main() {
    val inputs = listOf("src/day01/test.txt", "src/day01/official.txt")
        .map(::parseInput)

    println("Task 1:")
    inputs.forEach { (list1, list2) ->
        println(getTotalDistance(list1, list2))
    }

    println("Task 2:")
    inputs.forEach { (list1, list2) ->
        println(getSimilarityScore(list1, list2))
    }
}