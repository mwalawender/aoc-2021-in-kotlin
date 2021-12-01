fun main() {
    fun part1(input: List<String>) =
        input.map(String::toInt)
            .zipWithNext()
            .count { measurement -> measurement.second > measurement.first }

    fun part2(input: List<String>) =
        input.map(String::toInt)
            .windowed(3, 1, false)
            .zipWithNext()
            .count { threeMeasurement ->
                threeMeasurement.second.sum() > threeMeasurement.first.sum()
            }


    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
