import kotlin.math.log10
import kotlin.math.pow

fun main() {
    fun parseEquations(input: List<String>): List<List<Long>> {
        val equations = input.map {
            "\\d+".toRegex().findAll(it).map { n -> n.value.toLong() }.toList()
        }
        return equations
    }

    fun part1(input: List<String>): Long {
        val equations = parseEquations(input)

        var totalCalibrationResult = 0L
        equations.forEach { numbers ->
            val calibrationResult = numbers.first()
            val operands = numbers.drop(1)
            val operatorCount = operands.size - 1
            val differentCombinationsCount = 1.shl(operatorCount) // 2^n

            for (combination in 0..<differentCombinationsCount) {
                // 0 or false = +
                // 1 or true = *
                val operators = (0..<operatorCount).map { combination.shr(it) and 0b1 == 0b1 }

                val evaluatedCalibrationResult = operands.drop(1).foldIndexed(operands.first()) { index, acc, operand ->
                    if (operators[index]) {
                        acc * operand
                    } else {
                        acc + operand
                    }
                }

                if (evaluatedCalibrationResult == calibrationResult) {
                    totalCalibrationResult += calibrationResult
                    break
                }
            }
        }
        return totalCalibrationResult
    }

    fun part2(input: List<String>): Long {
        val equations = parseEquations(input)

        var totalCalibrationResult = 0L
        equations.forEach { numbers ->
            val calibrationResult = numbers.first()
            val operands = numbers.drop(1)
            val operatorCount = operands.size - 1
            val differentCombinationsCount = 3.0.pow(operatorCount).toInt()

            for (combination in 0..<differentCombinationsCount) {
                val operators = (0..<operatorCount).map {
                    var ternary = combination
                    repeat(it) {
                        ternary /= 3
                    }
                    ternary % 3
                }

                val evaluatedCalibrationResult = operands.drop(1).foldIndexed(operands.first()) { index, acc, operand ->
                    when (operators[index]) {
                        0 -> acc * operand
                        1 -> acc + operand
                        else -> {
                            // Concatenation
                            val digitCount = log10(operand.toFloat()).toInt() + 1.0
                            (acc * 10.0.pow(digitCount).toLong()) + operand
                        }
                    }
                }

                if (evaluatedCalibrationResult == calibrationResult) {
                    totalCalibrationResult += calibrationResult
                    break
                }
            }
        }
        return totalCalibrationResult
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
