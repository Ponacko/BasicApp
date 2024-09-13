package com.example.basicapp

fun safeDivide(a: Int?, b: Int?): Int? = if (a == null || b == null) {
    null
} else {
    a / b
}

fun String.countVowels(): Int {
    val vowels = setOf('a', 'e', 'i', 'o', 'u', 'y')
    return this.count { it.lowercaseChar() in vowels }
}

fun main() {
    testTask1()
    testTask2()
}

private fun testTask1() {
    val listA = listOf(1, 2, null, null)
    val listB = listOf(1, null, 2, null)
    for (i in listA.indices) {
        val a = listA[i]
        val b = listB[i]
        println("${a.toString()}/$b=${safeDivide(a, b)}")
    }
}

private fun testTask2() {
    val testString =
        "Create an extension function for the String class that returns the number of vowels in the string."
    println("Number of vowels: ${testString.countVowels()}")
}