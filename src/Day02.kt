import java.lang.IllegalArgumentException

fun main() {
    fun part1(input: List<String>): Int {
        val horizontalWithDepth = input
            .map { line -> line.split(" ") }
            .map { list ->
                when (list.first()) {
                    "forward" -> Command.Forward(list.last().toInt())
                    "down" -> Command.Down(list.last().toInt())
                    "up" -> Command.Up(list.last().toInt())
                    else -> throw IllegalArgumentException("Invalid command!")
                }
            }.fold(Pair(0, 0)) { result, command ->
                when (command) {
                    is Command.Forward -> Pair(result.first + command.units, result.second)
                    is Command.Down -> Pair(result.first, result.second + command.units)
                    is Command.Up -> Pair(result.first, result.second - command.units)
                }
            }
        return horizontalWithDepth.first * horizontalWithDepth.second
    }


    fun part2(input: List<String>): Int {
        val horizontalWithDepth = input
            .map { line -> line.split(" ") }
            .map { list ->
                when (list.first()) {
                    "forward" -> Command.Forward(list.last().toInt())
                    "down" -> Command.Down(list.last().toInt())
                    "up" -> Command.Up(list.last().toInt())
                    else -> throw IllegalArgumentException("Invalid command!")
                }
            }.fold(SubmarineState(horizontalPosition = 0, aim = 0, depth = 0)) { result, command ->
                val state = when (command) {
                    is Command.Forward -> SubmarineState(
                        horizontalPosition = result.horizontalPosition + command.units,
                        aim = result.aim,
                        depth = result.depth + (command.units * result.aim)
                    )
                    is Command.Down -> SubmarineState(
                        horizontalPosition = result.horizontalPosition,
                        aim = result.aim + command.units,
                        depth = result.depth
                    )
                    is Command.Up -> SubmarineState(
                        horizontalPosition = result.horizontalPosition,
                        aim = result.aim - command.units,
                        depth = result.depth
                    )
                }
                println(state)
                state
            }
        println(horizontalWithDepth)
        return horizontalWithDepth.horizontalPosition * horizontalWithDepth.depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class SubmarineState(
    val horizontalPosition: Int,
    val aim: Int,
    val depth: Int,
)

sealed class Command(val units: Int) {
    class Forward(units: Int) : Command(units)
    class Down(units: Int) : Command(units)
    class Up(units: Int) : Command(units)
}