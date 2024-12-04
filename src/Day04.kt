data class Position(val x: Int, val y: Int)

val ZeroDirection = Position(0, 0)

val HorizontalDirections = listOf(
    Position(x = -1, y = 0),
    ZeroDirection,
    Position(x = 1, y = 0),
)

val VerticalDirections = listOf(
    Position(x = 0, y = -1),
    ZeroDirection,
    Position(x = 0, y = 1),
)

val BackSlashDiagonalDirections = listOf(
    Position(x = -1, y = -1),
    ZeroDirection,
    Position(x = 1, y = 1),
)

val SlashDiagonalDirections = listOf(
    Position(x = 1, y = -1),
    ZeroDirection,
    Position(x = -1, y = 1),
)

val AroundDirections = (HorizontalDirections + VerticalDirections + BackSlashDiagonalDirections + SlashDiagonalDirections)

val DiagonalDirectionsList = listOf(BackSlashDiagonalDirections, SlashDiagonalDirections)

fun main() {
    fun List<String>.extractLetterPositions(
        targetLetter: Char
    ): List<Position> {
        val positions = mutableListOf<Position>()
        forEachIndexed { c, line ->
            line.forEachIndexed { r, letter ->
                if (letter == targetLetter) {
                    positions += Position(r, c)
                }
            }
        }
        return positions
    }

    fun part1(input: List<String>): Int {
        val xPositions = input.extractLetterPositions('X')

        var xmasCounter = 0
        xPositions.forEach { (x, y) ->
            xmasCounter += AroundDirections.count { (dx, dy) ->
                if (dx == 0 && dy == 0) return@count false

                val xBeginWord = (0..<4).mapNotNull { next ->
                    input.getOrNull(y + (dy * next))?.getOrNull(x + (dx * next))
                }.joinToString(separator = "")

                xBeginWord == "XMAS"
            }
        }
        return xmasCounter
    }

    fun part2(input: List<String>): Int {
        val aPositions = input.extractLetterPositions('A')

        return aPositions.count { (x, y) ->
            DiagonalDirectionsList.all { diagonalDirections ->
                diagonalDirections.joinToString(separator = "") { (sx, sy) ->
                    (input.getOrNull(y + sy)?.getOrNull(x + sx) ?: ' ').toString()
                }.let {
                    it == "MAS" || it == "SAM"
                }
            }
        }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}