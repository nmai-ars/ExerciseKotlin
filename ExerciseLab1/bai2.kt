// bai2.kt
import kotlin.math.PI

fun main() {
    // Danh sách chỉ đọc & danh sách mutable
    val numbers = listOf(1, 2, 3, 4, 5, 6)
    println("numbers.size = ${numbers.size}")
    println("numbers[0] = ${numbers[0]}")
    println("reversed = ${listOf("red", "blue", "green").reversed()}")

    val entrees = mutableListOf<String>()
    entrees.add("spaghetti")
    println("entrees = $entrees")
    entrees[0] = "lasagna"
    println("entrees after update = $entrees")
    entrees.remove("lasagna")
    println("entrees after remove = $entrees")

    // for loop :contentReference[oaicite:16]{index=16}
    val myList = listOf("A", "B", "C")
    for (element in myList) {
        println("for: $element")
    }

    // while loop :contentReference[oaicite:17]{index=17}
    var index = 0
    while (index < myList.size) {
        println("while: ${myList[index]}")
        index++
    }

    // String length + template :contentReference[oaicite:18]{index=18}
    val name = "Android"
    println("name.length = ${name.length}")
    val number = 10
    val groups = 5
    println("$number people")
    println("${number * groups} people")

    // OOP: Dwelling / RoundHut / SquareCabin contentReference[oaicite:19]{index=19}
    val roundHut = RoundHut(residents = 3, radius = 2.5)
    val squareCabin = SquareCabin(residents = 4, sideLength = 3.0)

    // with(...) :contentReference[oaicite:20]{index=20}
    with(squareCabin) {
        println("Capacity: $capacity")
        println("Material: $buildingMaterial")
        println("Has room? ${hasRoom()}")
        println("Floor area: ${floorArea()}")
    }

    println("RoundHut floor area: ${roundHut.floorArea()}")

    // PI dùng trực tiếp hoặc fully-qualified :contentReference[oaicite:21]{index=21}
    val radius = 2.0
    println("Circle area by PI import: ${PI * radius * radius}")
    println("Circle area by fully-qualified: ${kotlin.math.PI * radius * radius}")

    // vararg :contentReference[oaicite:22]{index=22}
    addToppings("cheese", "pepperoni", "olives")
}

// abstract class + abstract members :contentReference[oaicite:23]{index=23}
abstract class Dwelling(private var residents: Int) {
    abstract val buildingMaterial: String
    abstract val capacity: Int
    abstract fun floorArea(): Double

    fun hasRoom(): Boolean = residents < capacity
    fun getResidentCount(): Int = residents
}

// open class để kế thừa :contentReference[oaicite:24]{index=24}
open class RoundHut(residents: Int, private val radius: Double) : Dwelling(residents) {
    override val buildingMaterial: String = "Straw"
    override val capacity: Int = 4

    override fun floorArea(): Double = PI * radius * radius
}

// class con + override :contentReference[oaicite:25]{index=25}
class SquareCabin(residents: Int, private val sideLength: Double) : Dwelling(residents) {
    override val buildingMaterial: String = "Wood"
    override val capacity: Int = 6

    override fun floorArea(): Double = sideLength * sideLength
}

fun addToppings(vararg toppings: String) {
    println("Toppings: ${toppings.joinToString()}")
}

/*
Ví dụ "package ..." trong quick guide là để minh hoạ cấu trúc dự án Android/Kotlin. :contentReference[oaicite:26]{index=26}
Nếu bạn dùng Kotlin file độc lập, để package theo cấu trúc thư mục của bạn.

package com.example.affirmations.model
*/
