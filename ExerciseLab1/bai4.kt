// bai4.kt
// xài try catch, thread, object, enum
import kotlin.random.Random

fun main() {
    println("=== BAI 4 ===")


    val t = Thread {
        val output = getValueBlocking()
        println("Thread got value = $output (thread=${Thread.currentThread().name})")
    }
    t.start()



    t.join()


    try {
        riskyWork()
    } catch (e: Exception) {
        println("Caught exception: ${e.message}")
    }

    // 3) object (singleton)
    DataProviderManager.log("Hello from object singleton")

    // 4) enum + when
    val direction = Direction.NORTH
    when (direction) {
        Direction.NORTH -> println("Go up")
        Direction.SOUTH -> println("Go down")
        Direction.WEST  -> println("Go left")
        Direction.EAST  -> println("Go right")
    }

    println("Done.")
}


fun getValueBlocking(): Double {
    try {
        Thread.sleep(300) // giả lập chờ
    } catch (_: InterruptedException) {
        // bị interrupt
        return -1.0
    }
    return Random.nextDouble()
}

fun riskyWork() {
    throw IllegalStateException("Something went wrong")
}

object DataProviderManager {
    fun log(message: String) = println("DataProviderManager: $message")
}

enum class Direction {
    NORTH, SOUTH, WEST, EAST
}
