import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val splitInput = input.map { it.split("   ").map(String::toInt) }
        val leftIdsList = splitInput.map { it[0] }.sorted()
        val rightIdsList = splitInput.map { it[1] }.sorted()
        return leftIdsList.mapIndexed { index, id ->
            abs(id - rightIdsList[index])
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val splitInput = input.map { it.split("   ").map(String::toInt) }
        val leftIds = splitInput.map { it[0] }
        val rightIds = splitInput.map { it[1] }
        return leftIds.sumOf { id ->
            val repeatCount = rightIds.count { it == id }
            id * repeatCount
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}