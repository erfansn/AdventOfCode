package test

import Day07
import org.openjdk.jmh.annotations.*
import readInput

@State(Scope.Benchmark)
class Day7Benchmark {

    private final var input = readInput("Day07")
    val day07 = Day07(input)

    @Benchmark
    fun part1TreeStructure(): String {
        return day07.part1()
    }

    @Benchmark
    fun part2TreeStructure(): String {
        return day07.part2()
    }
}
