fun main() {
    val input = readInput("Day03")
    
    // Steps:
    // - Collect non-point symbols in a list
    // - Detect numbers with length and start coordinate
    // - Check top-left-bottom-right of number if there's any symbol add to accumulator
    fun part1(input: List<String>): Int {
        val numbers = input.flatMapIndexed { index, line ->
            val numbers = "\\d+".toRegex().findAll(line)
            numbers.map {
                Number(
                    it.value,
                    it.range.first,
                    index,
                )
            }
        }
        
        return numbers.map { (value, x, y) ->
            // x + (- 1..value.length), y - 1
            val topChars = buildList {
                val line = input.getOrNull(y - 1).orEmpty()
                for (topX in -1..value.length) {
                    this += line.getOrElse(x + topX) { '.' }
                }
            }

            // x + (- 1), y
            val leftChar = input[y].getOrElse(x + -1) { '.' }

            // x + (- 1..value.length), y + 1
            val bottomChars = buildList {
                val line = input.getOrNull(y + 1).orEmpty()
                for (bottomX in -1..value.length) {
                    this += line.getOrElse(x + bottomX) { '.' }
                }
            }

            // x + value.length, y
            val rigthChar = input[y].getOrElse(x + value.length) { '.' }

            value to (topChars + leftChar + bottomChars + rigthChar)
        }.filter { (_, aroundSymbols) ->
            aroundSymbols.any { it != '.' && !it.isDigit() }
        }.sumOf { (value, _) ->
            value.toInt()
        }
    }
    check(part1(readInput("Day03_part1_test")) == 4361)
    println(part1(input))
    
    // Steps:
    // - Detect numbers with length and start coordinate
    // - Filter value with its coordinate of astriks
    // - Group values by coordinate of astriks
    // - Select groups with 2 numbers and mutliply them then add to accumulator
    fun part2(input: List<String>): Int {
        val numbers = input.flatMapIndexed { index, line ->
            val numbers = "\\d+".toRegex().findAll(line)
            numbers.map {
                Number(
                    it.value,
                    it.range.first,
                    index
                )
            }
        }
        
        return numbers.flatMap { (value, x, y) ->
                val aroundSymbolsCoordinate = listOf(
                    (y - 1) to ((-1)..value.length).map { x + it },
                    y to listOf(x + -1, x + value.length),
                    (y + 1) to ((-1)..value.length).map { x + it },
                )
    
                buildList {
                    for ((ay, xs) in aroundSymbolsCoordinate) {
                        val line = input.getOrNull(ay) ?: continue
                        for (charIndex in xs) {
                            if ((line.getOrNull(charIndex) ?: continue) == '*') {
                                this += GearAssociatedNumber(Point(charIndex, ay), value.toInt())
                            }
                        }
                    }
                }
            }
            .groupBy {
                it.location
            }
            .filterValues {
                it.size == 2
            }
            .map { (_, value) ->
                value[0].number * value[1].number
            }
            .sum()
    }
    check(part2(readInput("Day03_part2_test")) == 467835)
    println(part2(input))
}

data class Number(val value: String, val startX: Int, val startY: Int)

data class Point(val x: Int, val y: Int)

data class GearAssociatedNumber(val location: Point, val number: Int)
