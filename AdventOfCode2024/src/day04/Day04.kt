package day04

import java.io.File

fun parseInput(fileName: String): List<List<Char>> {
    return File(fileName).readLines().map { it.toList() }
}

fun transpose(matrix: List<List<Char>>): List<List<Char>> {
    if (matrix.isEmpty() || matrix.any { it.isEmpty() }) return emptyList()

    val numRows = matrix.size
    val numCols = matrix[0].size

    return (0 until numCols).map { col ->
        (0 until numRows).map { row ->
            matrix[row][col]
        }
    }
}

fun getAllFourElementLists(matrix: List<List<Char>>): List<List<Char>> {
    val result = mutableListOf<List<Char>>()
    for (row in matrix) {
        for (i in row.indices) {
            if (i + 4 <= row.size) {
                result.add(row.subList(i, i + 4))
            }
        }
    }
    return result
}

fun getAllFourElementDiagonals(matrix: List<List<Char>>): List<List<Char>> {
    val result = mutableListOf<List<Char>>()
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (i + 4 <= matrix.size && j + 4 <= matrix[i].size) {
                result.add(listOf(matrix[i][j], matrix[i + 1][j + 1], matrix[i + 2][j + 2], matrix[i + 3][j + 3]))
            }
        }
    }
    return result
}

fun getAllFourElementAntiDiagonals(matrix: List<List<Char>>): List<List<Char>> {
    val result = mutableListOf<List<Char>>()
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (i + 4 <= matrix.size && j - 3 >= 0) {
                result.add(listOf(matrix[i][j], matrix[i + 1][j - 1], matrix[i + 2][j - 2], matrix[i + 3][j - 3]))
            }
        }
    }
    return result
}

val validOne = listOf('X', 'M', 'A', 'S')
val validTwo = listOf('S', 'A', 'M', 'X')

fun get3x3Submatrix(matrix: List<List<Char>>, i: Int, j: Int, defaultChar: Char = ' '): List<List<Char>> {
    val submatrix = mutableListOf<List<Char>>()

    for (rowOffset in -1..1) {
        val row = mutableListOf<Char>()
        for (colOffset in -1..1) {
            val newRow = i + rowOffset
            val newCol = j + colOffset
            row.add(
                if (newRow in matrix.indices && newCol in matrix[newRow].indices) {
                    matrix[newRow][newCol]
                } else {
                    defaultChar
                }
            )
        }
        submatrix.add(row)
    }

    return submatrix
}

val first = listOf(listOf('M', ' ', 'S'),
                   listOf(' ', 'A', ' '),
                   listOf('M', ' ', 'S'))

val second = listOf(listOf('M', ' ', 'M'),
                    listOf(' ', 'A', ' '),
                    listOf('S', ' ', 'S'))

val third = listOf(listOf('S', ' ', 'M'),
                   listOf(' ', 'A', ' '),
                   listOf('S', ' ', 'M'))

val fourth = listOf(listOf('S', ' ', 'S'),
                    listOf(' ', 'A', ' '),
                    listOf('M', ' ', 'M'))

val validXs = listOf(first, second, third, fourth)

fun compareSubMatrices(submatrix1: List<List<Char>>, submatrix2: List<List<Char>>): Boolean {
    return submatrix1[0][0] == submatrix2[0][0] &&
            submatrix1[0][2] == submatrix2[0][2] &&
            submatrix1[2][0] == submatrix2[2][0] &&
            submatrix1[2][2] == submatrix2[2][2] &&
            submatrix1[1][1] == submatrix2[1][1]
}

fun getAllSubMatrices(matrix: List<List<Char>>): List<List<List<Char>>> {
    val result = mutableListOf<List<List<Char>>>()
    for (i in matrix.indices) {
        for (j in matrix[i].indices) {
            if (matrix[i][j] == 'A') {
                result.add(get3x3Submatrix(matrix, i, j))
            }
        }
    }
    return result
}

fun main() {
    val inputs = listOf("src/day04/test.txt", "src/day04/official.txt")
        .map(::parseInput)

    println("Task 1:")
    inputs.forEach { lines ->
        val allFourElementLists = getAllFourElementLists(lines) +
                getAllFourElementLists(transpose(lines)) +
                getAllFourElementDiagonals(lines) +
                getAllFourElementAntiDiagonals(lines)

        allFourElementLists.count { it == validOne || it == validTwo }.let(::println)
    }

    println("Task 2:")
    inputs.forEach { lines ->
        val allSubMatrices = getAllSubMatrices(lines)
        allSubMatrices.count { validXs.any { validX -> compareSubMatrices(it, validX) } }.let(::println)
    }

}