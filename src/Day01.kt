fun main() {
    fun part1(input: List<String>): Int {
        val foodPackCalories = mutableListOf<Int>()
        var elvesFoodCalorie = 0
        for (calorie in input.map(String::toIntOrNull)) {
            if (calorie == null) {
                foodPackCalories += elvesFoodCalorie
                elvesFoodCalorie = 0
                continue
            }
            elvesFoodCalorie += calorie
        }
        return foodPackCalories.max()
    }

    fun part2(input: List<String>): Int {
        val foodPackCalories = mutableListOf<Int>()
        var elvesFoodCalorie = 0
        for (calorie in input.map(String::toIntOrNull)) {
            if (calorie == null) {
                foodPackCalories += elvesFoodCalorie
                elvesFoodCalorie = 0
                continue
            }
            elvesFoodCalorie += calorie
        }
        return foodPackCalories.sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
