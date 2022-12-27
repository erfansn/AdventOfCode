import java.util.LinkedList
import java.util.Stack

data class Move(
    val count: Int,
    val sourceStackIndex: Int,
    val targetStackIndex: Int,
)

fun List<String>.toMove() = map(String::toInt).let {
    Move(
        count = it[0],
        sourceStackIndex = it[1] - 1,
        targetStackIndex = it[2] - 1,
    )
}

const val CRATES_STACK_COUNT = 9
const val MAX_INITIAL_STACKS_SIZE = 8
const val PROCEDURE_START_LINE = 10

fun main() {
    fun List<String>.parseCratesStack(onCrateFind: (Int, Char) -> Unit) {
        take(MAX_INITIAL_STACKS_SIZE).reversed().forEach {
            it.chunked(4).map { crate ->
                "\\w".toRegex().find(crate.trim())?.groupValues?.first().orEmpty()
            }.forEachIndexed { index, crete ->
                if (crete.isEmpty()) return@forEachIndexed
                onCrateFind(index, crete.first())
            }
        }
    }

    fun List<String>.parseProcedures(procedure: (Move) -> Unit) {
        drop(PROCEDURE_START_LINE).forEach {
            """move (\d+) from (\d) to (\d)""".toRegex()
                .matchEntire(it)
                ?.groupValues
                ?.drop(1)
                ?.toMove()
                ?.let(procedure)
        }
    }

    fun part1(input: List<String>): String {
        val cratesStack = Array(CRATES_STACK_COUNT) { Stack<Char>() }
        input.parseCratesStack { index, crate ->
            cratesStack[index] += crate
        }
        input.parseProcedures { (count, source, target) ->
            repeat(count) {
                cratesStack[source].pop().also(cratesStack[target]::push)
            }
        }
        return cratesStack.fold("") { acc, crateStack -> "$acc${crateStack.peek()}" }
    }

    fun part2(input: List<String>): String {
        val cratesStack = Array(CRATES_STACK_COUNT) { LinkedList<Char>() }
        input.parseCratesStack { index, crate ->
            cratesStack[index] += crate
        }
        input.parseProcedures { (count, source, target) ->
            val crates = cratesStack[source].takeLast(count)
            repeat(count) { cratesStack[source].removeLast() }
            cratesStack[target].addAll(crates)
        }
        return cratesStack.fold("") { acc, crateStack -> "$acc${crateStack.last}" }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}