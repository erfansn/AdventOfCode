fun main() {
    val input = readInput("Day02")

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (id, information) = it.split(": ")
            val cubesSet = information.split("; ")
            val cubes = cubesSet.map { set -> set.split(", ") }
            cubes.map {
                it.map {
                    val (number, color) = it.split(' ')
                    
                    when (color) {
                        "red" -> number.toInt() <= 12
                        "blue" -> number.toInt() <= 14
                        "green" -> number.toInt() <= 13
                        else -> {
                            println(color)
                            error("Impossible!")   
                        }
                    }
                }.all {
                    it == true
                }
            }.all {
                it == true
            }.takeIf {
                it == true
            }?.let {
                id.substringAfter(" ").toInt()
            } ?: run {
                0
            }
        }
    }
    check(part1(readInput("Day02_part1_test")) == 8)
    println(part1(input))
    
    fun part2(input: List<String>): Int {
        return input.sumOf {
            val (_, information) = it.split(": ")
            val cubesSet = information.split("; ")
            val cubes = cubesSet.map { set -> set.split(", ") }.flatMap { it }

            buildMap<_, Int> {
                cubes.map {
                    val (number, color) = it.split(' ')

                    if (getOrDefault(color, 0) < number.toInt()) {
                        this[color] = number.toInt()
                    }
                }
            }.let {
                it.values.reduce { acc, value -> acc * value }
            }
        }
    }
    check(part2(readInput("Day02_part2_test")) == 2286)
    println(part2(input))
}
