package com.rsschool.quiz

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

val style = listOf(
    R.style.Theme_Quiz_First,
    R.style.Theme_Quiz_Second,
    R.style.Theme_Quiz_Third,
    R.style.Theme_Quiz_Fourth,
    R.style.Theme_Quiz_Fifth
)

val quiz = listOf(
    QuizData("Кадр", listOf("Car", "Plant", "Frame", "Road", "Window"), 2),
    QuizData("Звезда", listOf("Star", "Fence", "Block", "Tree", "Sky"), 0),
    QuizData("Окно", listOf("View", "Data", "Train", "Road", "Window"), 4),
    QuizData("Камень", listOf("Earth", "Air", "Water", "Rock", "Fire"), 3),
    QuizData("Грязь", listOf("Swamp", "Dirt", "Sea", "Grass", "Water"), 1)
)

val quizansw = MutableList<Int?>(quiz.size) { null }


class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private lateinit var listener: DataPass
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DataPass
        val quizcount = arguments?.getInt(NUM) ?: 0
        context.setTheme(style[quizcount])
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val quiznum = arguments?.getInt(NUM) ?: 0
        val active = quiz[quiznum]
        binding.toolbar.title = "Question ${quiznum + 1}"
        binding.question.text = active.question
        binding.optionOne.text = active.option[0]
        binding.optionTwo.text = active.option[1]
        binding.optionThree.text = active.option[2]
        binding.optionFour.text = active.option[3]
        binding.optionFive.text = active.option[4]

        if (quizansw[quiznum] != null) {
            when (quizansw[quiznum]) {
                0 -> binding.optionOne.isChecked = true
                1 -> binding.optionTwo.isChecked = true
                2 -> binding.optionThree.isChecked = true
                3 -> binding.optionFour.isChecked = true
                4 -> binding.optionFive.isChecked = true
                else -> {
                }
            }
        }
        binding.nextButton.isEnabled = binding.radioGroup.checkedRadioButtonId != -1
        if (quiznum == 0) binding.previousButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.option_one -> quizansw[quiznum] = 0
                R.id.option_two -> quizansw[quiznum] = 1
                R.id.option_three -> quizansw[quiznum] = 2
                R.id.option_four -> quizansw[quiznum] = 3
                R.id.option_five -> quizansw[quiznum] = 4
            }
            binding.nextButton.isEnabled = true
        }


        if (quiznum == (quiz.size - 1)) binding.nextButton.text = "Submit"

        binding.toolbar.setNavigationOnClickListener {
            if (quiznum != 0) listener.OpenFragment(quiznum - 1)
        }

        binding.previousButton.setOnClickListener {
            listener.OpenFragment(quiznum - 1)
        }

        binding.nextButton.setOnClickListener {
            if (binding.nextButton.text == "Submit") {
                var right = 0
                var message = ""
                for (i in 0 until quizansw.size) {
                    if (quizansw[i] == quiz[i].answer) {
                        right++
                    }
                    message += "${i + 1}) ${quiz[i].question}\n" + "You answer: ${quiz[i].option[quizansw[i]!!]} \n\n"
                }
                val result = "Your result: ${right * 20} % "
                message = "Quiz results\n\n\n$result\n\n$message"
                listener.OpenResult(result, message)
            } else {
                binding.nextButton.isEnabled = binding.radioGroup.checkedRadioButtonId != -1
                listener.OpenFragment(quiznum + 1)
            }
        }
    }

    companion object {

        fun newInstance(numberQuestion: Int): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(NUM, numberQuestion)
            fragment.arguments = args
            return fragment
        }

        private const val NUM = "NUM"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
