package com.example.myapplication

import com.example.bowling2.MainActivity
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    lateinit var mainActivity: MainActivity

    @Before
    fun setup() {
        mainActivity = MainActivity()
    }
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun testCalculateScores() {
        mainActivity.scorepairs = arrayListOf(
            Pair(1, 4),
            Pair(4, 5),
            Pair(6, 4),
            Pair(5, 5),
            Pair(10, 0),
            Pair(0, 1),
            Pair(7, 3),
            Pair(6, 4),
            Pair(10, 0),
            Pair(2, 8),
            Pair(6, 0)
        )

        mainActivity.calculateScores()
        assertEquals(arrayListOf(5, 14, 29, 49, 60, 61, 77, 97, 117, 133), mainActivity.scoreList)
    }
}
