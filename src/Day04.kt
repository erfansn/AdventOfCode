fun main() {
    fun String.toRange() = split("-").map(String::toInt).let { (start, end) -> start..end }

    fun part1(input: List<String>): Int {
        return input.map {
            val (first, second) = it.split(",")
            first.toRange() to second.toRange()
        }.count { (first, second) ->
            (first.first <= second.first && first.last >= second.last) ||
            (first.first >= second.first && first.last <= second.last)
        }
    }

    fun part2(input: List<String>): Int {
        return input.map {
            val (first, second) = it.split(",")
            first.toRange() to second.toRange()
        }.count { (first, second) ->
            !(first.first > second.last || first.last < second.first)
        }
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
