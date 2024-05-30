package daa.gavrilova.solveexample

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.abs
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var operand1View: TextView
    private lateinit var operand2View: TextView
    private lateinit var operatorView: TextView
    private lateinit var btnStart: Button
    private lateinit var btnCheck: Button
    private lateinit var edtAnswer: EditText
    private lateinit var answerRightView: TextView
    private lateinit var answerWrongView: TextView
    private lateinit var solveExampleCountView: TextView // Добавляем переменную для счетчика SolveExamleCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        operand1View = findViewById(R.id.Operand1)
        operand2View = findViewById(R.id.Operand2)
        operatorView = findViewById(R.id.Operator)
        btnStart = findViewById(R.id.btnStart)
        btnCheck = findViewById(R.id.btnCheck)
        edtAnswer = findViewById(R.id.edtAnswer)
        answerRightView = findViewById(R.id.AnswerRight)
        answerWrongView = findViewById(R.id.AnswerWrong)
        solveExampleCountView = findViewById(R.id.SolveExamleCount) // Инициализируем счетчик

        // Инициализация кнопок
        btnStart.isEnabled = true
        btnCheck.isEnabled = false

        btnStart.setOnClickListener {
            edtAnswer.setText("") // Очистка EditText перед генерацией нового примера
            edtAnswer.setBackgroundColor(Color.BLACK)
            generateExample()
            btnStart.isEnabled = false // Деактивация кнопки "Старт" после генерации примера
            btnCheck.isEnabled = true // Активация кнопки "Проверка"
        }

        btnCheck.setOnClickListener {
            checkAnswer()
            btnCheck.isEnabled = false // Деактивация кнопки "Проверка" после проверки ответа
            btnStart.isEnabled = true // Активация кнопки "Старт"

            // Прибавляем 1 к счетчику SolveExamleCount после каждого примера
            incrementSolveExampleCount()
        }
    }

    private fun generateExample() {
        val random = Random()
        val operations = listOf('*', '/', '-', '+')

        operand1View.text = (random.nextInt(90) + 10).toString() // Генерация числа от 10 до 99
        operand2View.text = (random.nextInt(90) + 10).toString() // Генерация другого числа от 10 до 99

        operatorView.text = operations.random().toString() // Выбор случайной операции
    }

    private fun checkAnswer() {
        //val container = findViewById<RelativeLayout>(R.id.exampleContainer)

        val userInput = edtAnswer.text.toString().toIntOrNull()
        val result = calculateResult()

        if (userInput!= null && userInput == result) {
            incrementCorrectAnswers()
            edtAnswer.setBackgroundColor(Color.GREEN)

        } else {
            incrementIncorrectAnswers()
            edtAnswer.setBackgroundColor(Color.RED)

        }
    }

    private fun calculateResult(): Int {
        val op = operatorView.text.toString()[0]
        return when (op) {
            '+' -> operand1View.text.toString().toInt() + operand2View.text.toString().toInt()
            '-' -> operand1View.text.toString().toInt() - operand2View.text.toString().toInt()
            '*' -> operand1View.text.toString().toInt() * operand2View.text.toString().toInt()
            '/' -> operand1View.text.toString().toInt() / operand2View.text.toString().toInt()
            else -> 0
        }
    }

    private fun incrementCorrectAnswers() {
        val currentCount = answerRightView.text.toString().toIntOrNull()?: 0
        answerRightView.text = (currentCount + 1).toString()

    }

    private fun incrementIncorrectAnswers() {
        val currentCount = answerWrongView.text.toString().toIntOrNull()?: 0
        answerWrongView.text = (currentCount + 1).toString()

    }

    private fun incrementSolveExampleCount() { // Функция для инкремента счетчика SolveExamleCount
        val currentCount = solveExampleCountView.text.toString().toIntOrNull()?: 0
        solveExampleCountView.text = (currentCount + 1).toString()
    }


}
