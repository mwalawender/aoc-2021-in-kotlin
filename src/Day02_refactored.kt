data class SimpleSubmarineState(val horizontal: Int, val depth: Int)
data class AimedSubmarineState(val horizontal: Int, val depth: Int, val aim: Int)

sealed class Cmd(val units: Int) {
    abstract fun execute(state: SimpleSubmarineState): SimpleSubmarineState
    abstract fun execute(state: AimedSubmarineState): AimedSubmarineState

    companion object {
        fun create(text: String): Cmd {
            val (command, value) = text.split(" ")
            val intValue = value.toInt()
            return when (command) {
                "forward" -> Forward(intValue)
                "down" -> Down(intValue)
                "up" -> Up(intValue)
                else -> error("Invalid command!")
            }
        }
    }
}

class Forward(units: Int) : Cmd(units) {
    override fun execute(state: SimpleSubmarineState): SimpleSubmarineState =
        state.copy(horizontal = state.horizontal + units)

    override fun execute(state: AimedSubmarineState): AimedSubmarineState =
        state.copy(horizontal = state.horizontal + units, depth = state.depth + state.aim * units)
}

class Down(units: Int) : Cmd(units) {
    override fun execute(state: SimpleSubmarineState): SimpleSubmarineState = state.copy(depth = units + state.depth)

    override fun execute(state: AimedSubmarineState): AimedSubmarineState = state.copy(aim = state.aim + units)
}

class Up(units: Int) : Cmd(units) {
    override fun execute(state: SimpleSubmarineState): SimpleSubmarineState = state.copy(depth = state.depth - units)

    override fun execute(state: AimedSubmarineState): AimedSubmarineState = state.copy(aim = state.aim - units)
}

fun part1(input: List<String>): Int {
    val finalState = input
        .map { Cmd.create(it) }
        .fold(SimpleSubmarineState(0, 0)) { acc, command ->
            command.execute(acc)
        }
    return finalState.horizontal * finalState.depth
}

fun part2(input: List<String>): Int {
    val finalState = input
        .map { Cmd.create(it) }
        .fold(AimedSubmarineState(0, 0, 0)) { acc, command ->
            command.execute(acc)
        }
    return finalState.horizontal * finalState.depth
}

fun main() {
    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}