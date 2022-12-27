const val PACKET_SIZE = 4
const val MESSAGE_SIZE = 14

fun main() {
    fun generateSignal(input: List<String>) = input.single()
        .toCharArray()
        .asSequence()

    fun part1(input: List<String>): String {
        val firstPacketMarker = generateSignal(input)
            .windowed(size = PACKET_SIZE)
            .mapIndexed { index, marker -> index to marker }
            .find { (_, marker) ->
                marker.let { it.toSet().size == it.size }
            }

        return "${(firstPacketMarker?.first ?: 0) + PACKET_SIZE}"
    }

    fun part2(input: List<String>): String {
        val firstMessageMarker = generateSignal(input)
            .windowed(size = MESSAGE_SIZE)
            .mapIndexed { index, marker -> index to marker }
            .find { (_, marker) ->
                marker.let { it.toSet().size == it.size }
            }

        return "${(firstMessageMarker?.first ?: 0) + MESSAGE_SIZE}"
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}