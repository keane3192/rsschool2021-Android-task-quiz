package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), DataPass {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        OpenFragment(0)
    }

    override fun OpenFragment(quizcount: Int) {
        val fragment = QuizFragment.newInstance(quizcount)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun OpenResult(quizres: String, msg: String) {
        val fragment = QuizResult.newInstance(quizres, msg)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}
