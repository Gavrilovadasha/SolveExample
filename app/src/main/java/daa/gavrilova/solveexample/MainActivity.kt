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

            val AnswerRight = findViewById<TextView>(R.id.AnswerRight)
            val SolveExamleCount = findViewById<TextView>(R.id.SolveExamleCount)


            // Вычисление статистики
            val statistics = 100 * AnswerRight.text.toString().toInt() / SolveExamleCount.text.toString().toInt()

            // Обновление текста в TextView для отображения статистики
            findViewById<TextView>(R.id.Statistics).text = statistics.toString()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Сохраняем текущее состояние UI
        outState.putString("OPERAND_1", operand1View.text.toString())
        outState.putString("OPERAND_2", operand2View.text.toString())
        outState.putString("OPERATOR", operatorView.text.toString())
        outState.putBoolean("BTN_START_ENABLED", btnStart.isEnabled)
        outState.putBoolean("BTN_CHECK_ENABLED", btnCheck.isEnabled)
        outState.putString("ANSWER_RIGHT", answerRightView.text.toString())
        outState.putString("ANSWER_WRONG", answerWrongView.text.toString())
        outState.putInt("SOLVE_EXAMPLE_COUNT", solveExampleCountView.text.toString().toInt())

        // Если есть введенный пользователем ответ, сохраняем его
        if (edtAnswer.text.isNotEmpty()) {
            outState.putString("EDT_ANSWER", edtAnswer.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Восстановляем состояние UI
        operand1View.text = savedInstanceState.getString("OPERAND_1")
        operand2View.text = savedInstanceState.getString("OPERAND_2")
        operatorView.text = savedInstanceState.getString("OPERATOR")
        btnStart.isEnabled = savedInstanceState.getBoolean("BTN_START_ENABLED")
        btnCheck.isEnabled = savedInstanceState.getBoolean("BTN_CHECK_ENABLED")
        answerRightView.text = savedInstanceState.getString("ANSWER_RIGHT")
        answerWrongView.text = savedInstanceState.getString("ANSWER_WRONG")
        solveExampleCountView.text = savedInstanceState.getInt("SOLVE_EXAMPLE_COUNT").toString()

        // Если был сохранен введенный пользователем ответ, восстанавливаем его
        if (savedInstanceState.containsKey("EDT_ANSWER")) {
            edtAnswer.setText(savedInstanceState.getString("EDT_ANSWER"))
        }
    }

}
