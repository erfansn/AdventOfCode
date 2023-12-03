fun main() {
    val input = readInput("Day01")

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val isDigit = Char::isDigit
            val first = it.first(isDigit)
            val last = it.last(isDigit)
            "$first$last".toInt()
        }
    }
    check(part1(readInput("Day01_part1_test")) == 142)
    println(part1(input))

    fun part2(input: List<String>): Int {
        val digitWordsMap = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
        )
        val allPossibleValues = digitWordsMap.flatMap { listOf(it.key, it.value.toString()) }

        fun String.toDigit() = digitWordsMap[this] ?: toInt()

        return input.sumOf {
            val (_, first) = it.findAnyOf(allPossibleValues) ?: error("Impossible")
            val (_, last) = it.findLastAnyOf(allPossibleValues) ?: error("Impossible")
            first.toDigit() * 10 + last.toDigit()
        }
    }
    check(part2(readInput("Day01_part2_test")) == 281)
    println(part2(input))
}
