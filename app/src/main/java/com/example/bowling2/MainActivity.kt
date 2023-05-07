package com.example.bowling2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import kotlin.random.Random

lateinit var throwButton: Button
lateinit var pointsListView: ListView
lateinit var scoreListView: ListView
lateinit var totalScore: TextView


class MainActivity : AppCompatActivity() {

    var pointsList = ArrayList<String>()
    var scorepairs = ArrayList<Pair<Int, Int>>()
    var scoreList = ArrayList<Int>()
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        throwButton = findViewById(R.id.throwButton)
        pointsListView = findViewById(R.id.scoreList)
        scoreListView = findViewById(R.id.scoreTable)

        totalScore = findViewById(R.id.totalScore)


        initAdapters()




        throwButton.setOnClickListener {
            clearGame()
            simulateBowling()
            calculateScores()

            totalScore.text = "Total Score: $score "


        }

    }

    private fun initAdapters() {
        pointsListView.adapter = ArrayAdapter(
            this,
            R.layout.custom_list_item,
            R.id.list_item_textview,
            pointsList
        )
        scoreListView.adapter = ArrayAdapter(
            this,
            R.layout.custom_list_item,
            R.id.list_item_textview,
            scoreList
        )
    }

    fun simulateBowling() {
        //first 9 frames
        repeat(9) {
            val first = Random.nextInt(0, 11)
            val rest = 11 - first
            val second = Random.nextInt(0, rest)
            val total = first + second

            when {
                first == 10 -> {
                    // STRIKE
                    pointsList.add(" X")
                    scorepairs.add(first to 0)
                }
                total == 10 -> {
                    // SPARE
                    pointsList.add(" $first  |  / ")
                    scorepairs.add(first to second)
                }
                else -> {
                    pointsList.add(" $first  | $second  ")
                    scorepairs.add(first to second)
                }
            }
        }

        // 10th frame
        val first = Random.nextInt(0, 11)
        var rest = 11 - first
        val second = Random.nextInt(0, rest)
        val total = first + second

        when {
            first == 10 -> {
                // STRIKE
                pointsList.add("X")
                scorepairs.add(first to 0)

                // add a bonus throw
                val bonus = Random.nextInt(0, 11)
                if (bonus == 10) {
                    // if the bonus throw is also a strike, add one more throw
                    val secondbonus = Random.nextInt(0, 11)
                    pointsList.add(" extra: X | $secondbonus")
                    scorepairs.add(10 to secondbonus)
                    rest = 11
                } else {
                    pointsList.add(" $bonus ")
                    scorepairs.add(bonus to 0)
                    rest = 11 - bonus
                }
            }
            total == 10 -> {
                // SPARE
                pointsList.add(" $first  |  / ")
                scorepairs.add(first to second)

                // add a bonus throw
                val bonus = Random.nextInt(0, 11)
                if (bonus == 10) {
                    // if the bonus throw is also a strike, add one more throw
                    val secondbonus = Random.nextInt(0, 11)
                    pointsList.add("extra: X | $secondbonus")
                    scorepairs.add(10 to secondbonus)
                    rest = 11
                } else {
                    pointsList.add(" $bonus ")
                    scorepairs.add(bonus to 0)
                    rest = 11 - bonus
                }
            }
            else -> {
                // no strike or spare, add scores as usual
                pointsList.add(" $first  | $second  ")
                scorepairs.add(first to second)
            }
        }

        (pointsListView.adapter as ArrayAdapter<String>).notifyDataSetChanged()
        (scoreListView.adapter as ArrayAdapter<Int>).notifyDataSetChanged()
    }

    fun calculateScores() {
        var isEleventhPairPresent = false

        // Check if there is an 11th pair in scorepairs list
        if (scorepairs.size == 11) {
            isEleventhPairPresent = true
        }

        for (i in 0 until scorepairs.size - 1) {
            val pair = scorepairs[i]
            val nextPair = scorepairs[i + 1]

            when {
                pair.first == 10 && (i < 9 || !isEleventhPairPresent) -> {
                    //strike bonus
                    score += pair.first + nextPair.first + nextPair.second
                    scoreList.add(score)
                }
                pair.first + pair.second == 10 && (i < 9 || !isEleventhPairPresent) -> {
                    //spare bonus
                    score += pair.first + pair.second + nextPair.first
                    scoreList.add(score)
                }
                else -> {
                    //normal shot
                    score += pair.first + pair.second
                    scoreList.add(score)
                }
            }
        }

        // calculate the last score outside to avoid indexoutofbond
        if (isEleventhPairPresent) {
            val lastfirst = scorepairs[10].first
            val lastsecond = scorepairs[10].second
            score += lastfirst + lastsecond
            scoreList.add(score)
        } else {
            val lastfirst = scorepairs[9].first
            val lastsecond = scorepairs[9].second
            score += lastfirst + lastsecond
            scoreList.add(score)
        }

    }

    fun clearGame(){
        scoreList.clear()
        pointsList.clear()
        scorepairs.clear()
        score = 0
    }

}