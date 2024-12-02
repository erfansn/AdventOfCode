import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val levelsList = input.map { it.split(" ").map(String::toInt) }
        return levelsList.map { levels ->
            val levelsDifference = levels.windowed(2) { level -> level[1] - level[0] }
            val isAscendingOrDescending = levelsDifference.all { it > 0 } || levelsDifference.all { it < 0 }
            val isDifferenceInValidRange = levelsDifference.all { abs(it) in 1..3 }

            isAscendingOrDescending && isDifferenceInValidRange
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        val levelsList = input.map { it.split(" ").map(String::toInt) }

        fun isSafe(levels: List<Int>): Boolean {
            val levelsDifference = levels.windowed(2) { level -> level[1] - level[0] }
            val isAscendingOrDescending = levelsDifference.all { it > 0 } || levelsDifference.all { it < 0 }
            val isDifferenceInValidRange = levelsDifference.all { abs(it) in 1..3 }
            return isAscendingOrDescending && isDifferenceInValidRange
        }

        return levelsList.map { levels ->
            if (isSafe(levels)) return@map true

            for (i in levels.indices) {
                val modifiedLevels = levels.filterIndexed { index, _ -> i != index }
                if (isSafe(modifiedLevels)) return@map true
            }
            return@map false
        }.count { it }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}