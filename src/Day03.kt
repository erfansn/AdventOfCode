fun main() {
    fun uppercasePriorities(char: Char) = (char - 'A' + 27)
    fun lowercasePriorities(char: Char) = (char - 'a' + 1)

    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val (firstCompartment, secondCompartment) = rucksack.let { it.chunked(it.length / 2) }

            firstCompartment.first { it in secondCompartment }.let {
                if (it.isLowerCase()) lowercasePriorities(it) else uppercasePriorities(it)
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { (rucksack1, rucksack2, rucksack3) ->
            rucksack1.first { it in rucksack2 && it in rucksack3 }.let {
                if (it.isLowerCase()) lowercasePriorities(it) else uppercasePriorities(it)
            }
        }
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
