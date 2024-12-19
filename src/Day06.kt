import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

data class GuardPosition(val x: Int, val y: Int)

fun main() {
    fun initialGuardPosition(input: List<String>): GuardPosition {
        var guardPatrolStartPosition = GuardPosition(-1, -1)
        input.forEachIndexed { y, line ->
            line.indexOfFirst {
                it == '^'
            }.takeIf { it != -1 }?.let { x ->
                guardPatrolStartPosition = GuardPosition(x, y)
            }
        }
        return guardPatrolStartPosition
    }

    fun part1(input: List<String>): Int {
        val guardPatrolStartPosition = initialGuardPosition(input)

        var obstructionsCounter = 0
        var currentPosition = guardPatrolStartPosition
        val visitedPositions = mutableSetOf(currentPosition)
        do {
            val nextPosition = currentPosition.let {
                when (obstructionsCounter % 4) {
                    0 -> it.copy(y = it.y - 1)
                    1 -> it.copy(x = it.x + 1)
                    2 -> it.copy(y = it.y + 1)
                    else -> it.copy(x = it.x - 1)
                }
            }

            if (input[nextPosition] == '#') {
                obstructionsCounter++
            } else {
                visitedPositions += nextPosition
                currentPosition = nextPosition
            }
        } while (input[currentPosition] != null)

        return visitedPositions.size - 1
    }

    fun part2(input: List<String>): Int {
        fun List<String>.checkGuardInLoop(): Boolean {
            val guardPatrolStartPosition = initialGuardPosition(this)
            if (guardPatrolStartPosition == GuardPosition(-1, -1)) return false

            fun direction(obstructionsCount: Int) = obstructionsCount % 4

            var obstructionsCounter = 0
            var currentPosition = guardPatrolStartPosition
            val visitedPositions = mutableListOf(currentPosition to direction(obstructionsCounter))
            do {
                val nextPosition = currentPosition.let {
                    when (direction(obstructionsCounter)) {
                        0 -> it.copy(y = it.y - 1)
                        1 -> it.copy(x = it.x + 1)
                        2 -> it.copy(y = it.y + 1)
                        else -> it.copy(x = it.x - 1)
                    }
                }

                if (this[nextPosition] == '#') {
                    obstructionsCounter++
                } else {
                    (nextPosition to direction(obstructionsCounter)).let {
                        if (it in visitedPositions) return true
                    }
                    visitedPositions += nextPosition to direction(obstructionsCounter)
                    currentPosition = nextPosition
                }
            } while (this[currentPosition] != null)

            return false
        }

        fun mutableGuardMap() = input.toMutableList().map { it.toMutableList() }

        val loopCounter = AtomicInteger(0)
        runBlocking(Dispatchers.Default) {
            input.forEachIndexed { y, line ->
                line.forEachIndexed { x, _ ->
                    launch {
                        val newMap = mutableGuardMap().also { it[y][x] = '#' }
                            .map { it.joinToString(separator = "") }
                        if (newMap.checkGuardInLoop()) {
                            loopCounter.incrementAndGet()
                        }
                    }
                }
            }
        }

        return loopCounter.get()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

operator fun List<String>.get(position: GuardPosition): Char? {
    return this.getOrNull(position.y)?.getOrNull(position.x)
}
