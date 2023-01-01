import javax.swing.tree.TreeNode

fun main() {
    val input = readInput("Day07")

    val day07 = Day07(input)
    println(day07.part1())
    println(day07.part2())
}

class Day07(input: List<String>) : AocSolution {

    private val terminalOutput = input
        .joinToString(separator = "\n")
        .split("$ ")
        .drop(1)
        .map(String::trim)

    var current = Folder("/", parent = null)
    val root = current

    init {
        for (commandResult in terminalOutput.drop(1)) {
            when (commandResult.substring(0, 2)) {
                "ls" -> {
                    current.children += commandResult.lines().drop(1).map {
                        val (part1, part2) = it.split(" ")
                        when {
                            part1 == "dir" -> {
                                Folder(name = part2, parent = current)
                            }
                            part1.toIntOrNull() != null -> {
                                File(name = part2, parent = current, size = part1.toInt())
                            }
                            else -> error("Malformed input")
                        }
                    }
                }
                "cd" -> {
                    val path = commandResult.removePrefix("cd ")
                    current = if (path == "..") {
                        current.parent ?: error("illegal move!")
                    } else {
                        current.children.first { it.name == path } as? Folder ?: error("Tried to cd into a file")
                    }
                }
            }
        }
    }

    override fun part1(): String {
        return root.findFolders(100_000).sumOf(Node::size).toString()
    }

    override fun part2(): String {
        val totalDisk = 70_000_000
        val usedSpace = root.size()
        val freeSpace = totalDisk - usedSpace
        val neededSpace = 30_000_000
        val freeUpSpace = neededSpace - freeSpace
        return root.findFolders(Int.MAX_VALUE).filter { it.size() > freeUpSpace }.minOf { it.size() }.toString()
    }
}

sealed class Node(val name: String, val parent: Folder?) {
    abstract fun size(): Int
}

class File(
    name: String,
    parent: Folder?,
    private val size: Int
) : Node(name, parent) {

    override fun size() = size
}

class Folder(
    name: String,
    parent: Folder?,
    val children: MutableList<Node> = mutableListOf(),
) : Node(name, parent) {

    override fun size() = children.sumOf { it.size() }

    fun findFolders(maxSize: Int): List<Folder> {
        val children = children.filterIsInstance<Folder>().flatMap {
            it.findFolders(maxSize)
        }

        return if (size() <= maxSize) {
            children + this
        } else {
            children
        }
    }
}
