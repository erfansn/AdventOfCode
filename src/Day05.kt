fun main() {
    fun String.splitNumbers(delimiter: String): List<Int> {
        return split(delimiter).map(String::toInt)
    }

    fun List<String>.parseSleighLaunchManual() = run {
        val rules = takeWhile { "|" in it }
        val updates = dropWhile { it in rules || it.isEmpty() }.map { it.splitNumbers(",") }
        rules to updates
    }

    fun extractOrderedUpdates(
        updates: List<List<Int>>,
        rules: List<String>
    ): MutableList<List<Int>> {
        val orderedUpdates = mutableListOf<List<Int>>()
        updates.forEach { update ->
            val totalRightOrderPerUpdate = update.size * (update.size - 1)

            var rightOrderCounter = 0
            update.forEachIndexed { index, page ->
                for (i in update.indices) {
                    when {
                        i < index -> {
                            "${update[i]}|$page"
                        }

                        i > index -> {
                            "$page|${update[i]}"
                        }

                        else -> {
                            continue
                        }
                    }.let {
                        if (it in rules) rightOrderCounter++
                    }
                }
            }
            if (rightOrderCounter == totalRightOrderPerUpdate) orderedUpdates += update
        }
        return orderedUpdates
    }

    fun part1(input: List<String>): Int {
        val (rules, updates) = input.parseSleighLaunchManual()

        val orderedUpdates = extractOrderedUpdates(updates, rules)

        return orderedUpdates.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val (rules, updates) = input.parseSleighLaunchManual()

        val orderedUpdates = extractOrderedUpdates(updates, rules)
        val unorderedUpdates = updates.filter { it !in orderedUpdates }

        val rulesGroupedByX = rules.map { it.splitNumbers("|") }.groupBy(keySelector = { it[0] }, valueTransform = { it[1] })

        val fixedUpdates = mutableListOf<List<Int>>()
        unorderedUpdates.forEach { unorderedUpdate ->
            val orderedUpdate = mutableListOf<Int>()
            for (ySize in unorderedUpdate.indices.reversed()) {
                for (page in unorderedUpdate) {
                    if (page in orderedUpdate) continue

                    val isPageInRulesWithMinYSize = rulesGroupedByX[page]?.let { ys ->
                        val allUnorderedUpdatePagesInYsOfRules = unorderedUpdate.filterNot { it in orderedUpdate || it == page }.all { page -> page in ys }
                        ys.size >= ySize && allUnorderedUpdatePagesInYsOfRules
                    }
                    if (isPageInRulesWithMinYSize == true) {
                        orderedUpdate += page
                        break
                    }
                }
            }
            fixedUpdates += orderedUpdate
        }
        return fixedUpdates.sumOf { it[it.size / 2] }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
