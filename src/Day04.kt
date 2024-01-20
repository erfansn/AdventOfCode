import kotlin.math.pow

val input = readInput("Day04")
val testInput = readInput("Day04_test")

fun main() {
    fun String.countWinningNumbers(): Int {
        val allNumbers = substringAfter(": ")
        val (winningNumbers, myNumbers) = allNumbers.split(" | ")
        val winningsNumberList = winningNumbers.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }
        val myNumberList = myNumbers.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }

        return myNumberList.count { it in winningsNumberList }
    }
    
    fun part1(input: List<String>): Int {
        return input.sumOf {
            2.0.pow(it.countWinningNumbers() - 1).toInt()
        }
    }
    check(part1(testInput) == 13)
    println(part1(input))
    
    // Steps:
    // - Find count of winning cards then store it
    // - Add one unit to list from current card index + 1 until next cards by number of winning cards also consider previous ones
    // - Sum total of cards
    fun part2(input: List<String>): Int {
        val numbers = Array(input.size) { 1 }
        val winnerCards = Array(input.size) { 0 }
        input.forEachIndexed { index, it -> 
            val myWinningNumbersCount = it.countWinningNumbers()
            
            winnerCards[index] = myWinningNumbersCount
            repeat(numbers[index] - 1) {
                repeat(winnerCards[index]) {
                    numbers[index + it + 1] += 1 
                }
            }
            repeat(myWinningNumbersCount) {
                numbers[index + it + 1] += 1
            }
        }
        return numbers.sum()
    }
    check(part2(testInput) == 30)
    println(part2(input))
}
