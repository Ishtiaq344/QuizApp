package com.example.quizapp


import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import android.app.AlertDialog


class HomeActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var radioGroupOptions: RadioGroup
    private lateinit var radioButtonOption1: RadioButton
    private lateinit var radioButtonOption2: RadioButton
    private lateinit var radioButtonOption3: RadioButton
    private lateinit var nextButton: Button

    private var currentQuestionIndex = 0
    private var score = 0

    private val questions: MutableList<Question> = generateRandomQuestions(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        titleTextView = findViewById(R.id.title)
        questionTextView = findViewById(R.id.textViewQuestion)
        radioGroupOptions = findViewById(R.id.radioGroupOptions)
        radioButtonOption1 = findViewById(R.id.radioButtonOption1)
        radioButtonOption2 = findViewById(R.id.radioButtonOption2)
        radioButtonOption3 = findViewById(R.id.radioButtonOption3)
        nextButton = findViewById(R.id.buttonSubmit)

        updateQuestion()

        nextButton.setOnClickListener {
            val selectedOptionId = radioGroupOptions.checkedRadioButtonId

            if (selectedOptionId == -1) {
                // No option selected, show an alert message
                showOptionSelectionAlert()
            } else {
                // Option selected, check the answer and proceed to the next question
                checkAnswer()
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    updateQuestion()
                } else {
                    showResult()
                }
            }
        }
    }

    private fun showOptionSelectionAlert() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Alert")
        alertDialogBuilder.setMessage("Please select an option before proceeding.")

        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun updateQuestion() {
        if (currentQuestionIndex < questions.size) {
            val currentQuestion = questions[currentQuestionIndex]
            questionTextView.text = currentQuestion.question
            if (currentQuestion.options.size == 3) {
                radioButtonOption1.text = currentQuestion.options[0]
                radioButtonOption2.text = currentQuestion.options[1]
                radioButtonOption3.text = currentQuestion.options[2]
            } else {
                Log.e("HomeActivity", "Invalid options for question: ${currentQuestion.question}")
            }
            radioGroupOptions.clearCheck()
        } else {
            Log.e("HomeActivity", "No more questions")
        }
    }

    private fun checkAnswer() {
        val selectedOptionId = radioGroupOptions.checkedRadioButtonId

        when (selectedOptionId) {
            R.id.radioButtonOption1 -> checkResult(questions[currentQuestionIndex].options[0])
            R.id.radioButtonOption2 -> checkResult(questions[currentQuestionIndex].options[1])
            R.id.radioButtonOption3 -> checkResult(questions[currentQuestionIndex].options[2])
        }
    }

    private fun checkResult(selectedOption: String) {
        if (selectedOption == questions[currentQuestionIndex].correctAnswer) {
            score++
        }
    }

    private fun showResult() {
        val resultDialogView = layoutInflater.inflate(R.layout.dialog_result, null)
        val textViewResult = resultDialogView.findViewById<TextView>(R.id.textViewResult)
        val buttonRestart = resultDialogView.findViewById<Button>(R.id.buttonRestart)
        val buttonExit = resultDialogView.findViewById<Button>(R.id.buttonExit)

        textViewResult.text = "Quiz completed. Your Score: $score"

        val resultDialogBuilder = AlertDialog.Builder(this)
        resultDialogBuilder.setView(resultDialogView)

        val resultDialog = resultDialogBuilder.create()

        buttonRestart.setOnClickListener {
            resultDialog.dismiss()
            restartQuiz()
        }

        buttonExit.setOnClickListener {
            resultDialog.dismiss()
            finish()
        }

        resultDialog.show()
    }


    private fun restartQuiz() {
        // Reset quiz variables and start a new quiz
        currentQuestionIndex = 0
        score = 0
        questions.clear()
        questions.addAll(generateRandomQuestions(10))
        updateQuestion()
    }

    private fun generateRandomQuestions(count: Int): MutableList<Question> {
        val generatedQuestions: MutableList<Question> = mutableListOf()

        repeat(count) {
            val operand1 = Random.nextInt(10)
            val operand2 = Random.nextInt(10)
            val operator = when (Random.nextInt(4)) {
                0 -> "+"
                1 -> "-"
                2 -> "*"
                else -> "/"
            }
            val questionText = "What is $operand1 $operator $operand2?"
            val correctAnswer = calculateAnswer(operand1, operand2, operator)
            val options = generateOptions(correctAnswer)
            generatedQuestions.add(Question(questionText, correctAnswer, options))
        }

        return generatedQuestions
    }

    private fun calculateAnswer(operand1: Int, operand2: Int, operator: String): String {
        return when (operator) {
            "+" -> (operand1 + operand2).toString()
            "-" -> (operand1 - operand2).toString()
            "*" -> (operand1 * operand2).toString()
            "/" -> (operand1 / operand2).toString()
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }

    private fun generateOptions(correctAnswer: String): List<String> {
        val options = mutableListOf(correctAnswer)
        repeat(2) {
            val incorrectOption = generateRandomIncorrectOption(correctAnswer)
            options.add(incorrectOption)
        }
        return options.shuffled()
    }

    private fun generateRandomIncorrectOption(correctAnswer: String): String {
        var incorrectOption: String
        do {
            incorrectOption = (correctAnswer.toInt() + Random.nextInt(1, 10)).toString()
        } while (incorrectOption == correctAnswer)
        return incorrectOption
    }
}

data class Question(val question: String, val correctAnswer: String, val options: List<String> = listOf())
