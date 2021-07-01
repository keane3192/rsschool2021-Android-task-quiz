package com.rsschool.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentResultBinding
import kotlin.system.exitProcess

class QuizResult : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private lateinit var listener: DataPass
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as DataPass
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultText.text = arguments?.getString(QUIZRES)
        val message = arguments?.getString(MSG)
        binding.resultRefresh.setOnClickListener {
            for (i in 0 until quizansw.size) {
                quizansw[i] = null
            }
            listener.OpenFragment(0)
        }
        binding.resultClose.setOnClickListener {
            exitProcess(0)

        }
        binding.resultShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
    }

    companion object {

        fun newInstance(quizres: String, msg: String): QuizResult {
            val fragment = QuizResult()
            val args = Bundle()
            args.putString(QUIZRES, quizres)
            args.putString(MSG, msg)
            fragment.arguments = args
            return fragment
        }

        private const val QUIZRES = "QUIZRES"
        private const val MSG = "MSG"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
