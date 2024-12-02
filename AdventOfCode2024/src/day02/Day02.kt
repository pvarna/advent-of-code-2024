package day02

import java.io.File

fun parseInput(fileName: String): List<List<Int>> {
    val lines = File(fileName).readLines()

    return lines.map { line -> line.split(" ").map(String::toInt) }
}

fun reportIsSafelyIncreasing(report: List<Int>): Boolean {
    if (report.isEmpty() || report.size == 1) {
        return true
    }
    if (report[0] >= report[1] || report[1] - report[0] > 3) {
        return false
    }

    return reportIsSafelyIncreasing(report.drop(1))
}

fun reportIsSafelyDecreasing(report: List<Int>): Boolean {
    if (report.isEmpty() || report.size == 1) {
        return true
    }
    if (report[0] <= report[1] || report[0] - report[1] > 3) {
        return false
    }

    return reportIsSafelyDecreasing(report.drop(1))
}
fun allListsWithoutOne(inputList: List<Int>): List<List<Int>> {
    return List(inputList.size) { index ->
        inputList.filterIndexed { i, _ -> i != index }
    }
}

fun reportIsSafePartOne(report: List<Int>): Boolean {
    return reportIsSafelyIncreasing(report) || reportIsSafelyDecreasing(report)
}

fun reportIsSafePartTwo(report: List<Int>): Boolean {
    val allReports = listOf(report) + allListsWithoutOne(report)

    return allReports.any(::reportIsSafePartOne)
}

fun main() {
    val inputs = listOf("src/day02/test.txt", "src/day02/official.txt")
        .map(::parseInput)

    println("Task 1:")
    inputs.forEach { reports ->
        println(reports.count(::reportIsSafePartOne))
    }

    println("Task 2:")
    inputs.forEach { reports ->
        println(reports.count(::reportIsSafePartTwo))
    }
}