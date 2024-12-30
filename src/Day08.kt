data class Day08Point(val x: Int, val y: Int) {
    operator fun minus(other: Day08Point): Day08Line {
        return Day08Line(x - other.x, y - other.y)
    }
    operator fun plus(other: Day08Point): Day08Point {
        return Day08Point(x + other.x, y + other.y)
    }
    operator fun plus(other: Day08Line): Day08Point {
        return Day08Point(x + other.width, y + other.height)
    }
}

data class Day08Line(val width: Int, val height: Int) {
    operator fun times(i: Int): Day08Line {
        return Day08Line(width * i, height * i)
    }
}

fun main() {
    fun List<String>.createAntennasLookupMap() = buildMap<Char, MutableSet<Day08Point>> {
        forEachIndexed { y, line ->
            line.forEachIndexed { x, frequency ->
                if (frequency != '.') {
                    getOrPut(frequency) { mutableSetOf() } += Day08Point(x, y)
                }
            }
        }
    }

    fun List<String>.isInsideMap(point: Day08Point): Boolean {
        return point.y in indices && point.x in 0..<this[0].length
    }

    fun part1(input: List<String>): Int {
        val antennas = input.createAntennasLookupMap()

        val antinodes = buildSet {
            antennas.forEach { (_, points) ->
                for ((i, point) in points.withIndex()) {
                    for (copyPoint in points.drop(i + 1)) {
                        val deltaAntenna = (copyPoint - point)

                        this += point + (deltaAntenna * 2)
                        this += point + (deltaAntenna * -1)
                    }
                }
            }
        }

        return antinodes.count(input::isInsideMap)
    }
    
    fun part2(input: List<String>): Int {
        val antennas = input.createAntennasLookupMap()

        val antinodes = buildSet {
            antennas.forEach { (_, points) ->
                for ((i, point) in points.withIndex()) {
                    for (copyPoint in points.drop(i + 1)) {
                        val deltaAntenna = (copyPoint - point)

                        for (t in (-input.size)..input.size) {
                            this += point + (deltaAntenna * t)
                        }
                    }
                }
            }
        }

        return antinodes.count(input::isInsideMap)
    }
    
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
