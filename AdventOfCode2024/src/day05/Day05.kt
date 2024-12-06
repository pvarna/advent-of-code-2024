package day05

import java.io.File

fun divideListOnEmpty(strings: List<String>): Pair<List<String>, List<String>> {
    val emptyIndex = strings.indexOf("")
    return if (emptyIndex != -1) {
        val before = strings.subList(0, emptyIndex)
        val after = strings.subList(emptyIndex + 1, strings.size)
        Pair(before, after)
    } else {
        Pair(strings, emptyList())
    }
}

fun parseInput(fileName: String): Pair<List<Pair<Int, Int>>, List<List<Int>>> {
    val lines = File(fileName).readLines()
    val (pageOrderingRules, updates) = divideListOnEmpty(lines)
    val rules = pageOrderingRules.map { rule ->
        val (page, order) = rule.split("|").map(String::toInt)
        Pair(page, order)
    }

    val updatesAsInts = updates.map { it.split(",").map(String::toInt) }

    return Pair(rules, updatesAsInts)
}

fun checkUpdate(rules: List<Pair<Int, Int>>, update: List<Int>): Boolean {
    for (index in update.indices) {
        for (nextIndex in index + 1..<update.size) {
            val first = update[index]
            val second = update[nextIndex]
            if (!rules.any { (page, order) -> page == first && order == second }) {
                return false
            }
        }
    }

    return true
}

fun partOne(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Int {
    return updates.sumOf { if (checkUpdate(rules, it)) it[it.size / 2] else 0 }
}

fun partTwo(rules: List<Pair<Int, Int>>, updates: List<List<Int>>): Int {
    val incorrectUpdates = updates.filterNot { checkUpdate(rules, it) }

    val sortedUpdates = incorrectUpdates.map { update ->
        update.sortedWith { l, r ->
            if (rules.any { (page, order) -> page == l && order == r }) -1 else 1
        }
    }

    val correctSortedUpdates = sortedUpdates.filter { checkUpdate(rules, it) }

    return correctSortedUpdates.sumOf { it[it.size / 2] }
}

fun main() {
    val inputs = listOf("src/day05/test.txt", "src/day05/official.txt")
        .map(::parseInput)

    println("Task 1:")
    inputs.forEach { (rules, updates) ->
        println(partOne(rules, updates))
    }

    println("Task 2:")
    inputs.forEach { (rules, updates) ->
        println(partTwo(rules, updates))
    }

}