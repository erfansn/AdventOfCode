fun main() {
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val actualInput = readInput("Day03")
    part1(actualInput).println()
    part2(actualInput).println()
}

fun part1(input: List<String>): Int {
    return input.sumOf { corruptedData ->
        MUL_INSTRUCTION.toRegex().findAll(corruptedData).map {
            val (x, y) = it.destructured
            x.toInt() * y.toInt()
        }.sum()
    }
}

fun part2(input: List<String>): Int {
    var allowToCompute = true
    var totalSum = 0
    for (corruptedData in input) {
        totalSum += """$MUL_INSTRUCTION|do\(\)|don't\(\)""".toRegex().findAll(corruptedData).sumOf {
            when (it.value) {
                "do()" -> allowToCompute = true
                "don't()" -> allowToCompute = false
                else -> {
                    val (x, y) = it.destructured
                    if (allowToCompute) {
                        return@sumOf x.toInt() * y.toInt()
                    }
                }
            }
            0
        }
    }
    return totalSum
}

private val MUL_INSTRUCTION = """mul\((\d+),(\d+)\)"""
