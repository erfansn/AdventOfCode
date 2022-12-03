enum class HandShape(val score: Int) { ROCK(1), PAPER(2), SCISSORS(3) }
enum class RoundResult(val score: Int) { WIN(6), LOSS(0), DRAW(3) }

fun main() {
    fun part1(input: List<String>): Int {
        val tournamentFinalResult = mapOf(
            (HandShape.ROCK to HandShape.PAPER) to RoundResult.WIN,
            (HandShape.PAPER to HandShape.SCISSORS) to RoundResult.WIN,
            (HandShape.SCISSORS to HandShape.ROCK) to RoundResult.WIN,

            (HandShape.PAPER to HandShape.ROCK) to RoundResult.LOSS,
            (HandShape.SCISSORS to HandShape.PAPER) to RoundResult.LOSS,
            (HandShape.ROCK to HandShape.SCISSORS) to RoundResult.LOSS,

            (HandShape.ROCK to HandShape.ROCK) to RoundResult.DRAW,
            (HandShape.PAPER to HandShape.PAPER) to RoundResult.DRAW,
            (HandShape.SCISSORS to HandShape.SCISSORS) to RoundResult.DRAW,
        )

        val strategyDecryptor = mapOf(
            "A" to HandShape.ROCK,
            "B" to HandShape.PAPER,
            "C" to HandShape.SCISSORS,
            "X" to HandShape.ROCK,
            "Y" to HandShape.PAPER,
            "Z" to HandShape.SCISSORS,
        )

        fun calculateScore(opponentShape: HandShape, ownShape: HandShape): Int {
            return ownShape.score + tournamentFinalResult[opponentShape to ownShape]!!.score
        }

        return input.map {
            val (opponent, me) = it.split("\\s".toRegex())

            calculateScore(
                opponentShape = strategyDecryptor[opponent] ?: error("Invalid input"),
                ownShape = strategyDecryptor[me] ?: error("Invalid input")
            )
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val playerHandShapePredicator = mapOf(
            (HandShape.ROCK to RoundResult.WIN) to HandShape.PAPER,
            (HandShape.PAPER to RoundResult.WIN) to HandShape.SCISSORS,
            (HandShape.SCISSORS to RoundResult.WIN) to HandShape.ROCK,

            (HandShape.PAPER to RoundResult.LOSS) to HandShape.ROCK,
            (HandShape.SCISSORS to RoundResult.LOSS) to HandShape.PAPER,
            (HandShape.ROCK to RoundResult.LOSS) to HandShape.SCISSORS,

            (HandShape.ROCK to RoundResult.DRAW) to HandShape.ROCK,
            (HandShape.PAPER to RoundResult.DRAW) to HandShape.PAPER,
            (HandShape.SCISSORS to RoundResult.DRAW) to HandShape.SCISSORS,
        )

        val strategyDecryptor = mapOf(
            "A" to HandShape.ROCK,
            "B" to HandShape.PAPER,
            "C" to HandShape.SCISSORS,
            "X" to RoundResult.LOSS,
            "Y" to RoundResult.DRAW,
            "Z" to RoundResult.WIN,
        )

        fun predicateScore(opponentShape: HandShape, result: RoundResult): Int {
            return playerHandShapePredicator[opponentShape to result]!!.score + result.score
        }

        return input.map {
            val (opponent, result) = it.split("\\s".toRegex())

            predicateScore(
                opponentShape = strategyDecryptor[opponent] as HandShape,
                result = strategyDecryptor[result] as RoundResult
            )
        }.sum()
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
