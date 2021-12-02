fun main() {
    fun part1(input: List<Int>) =
        input.windowed(2).count { (a, b) -> a < b }

    fun part2(input: List<Int>) =
        input
            .windowed(3)
            .windowed(2)
            .count { (a, b) -> a.sum() < b.sum() }


    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}