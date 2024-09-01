package com.ucb.suficiencia.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private var tvDisplay: TextView? = null
    private var firstNum = 0.0
    private var secondNum = 0.0
    private var operator = ""
    private var isOperatorPressed = false
    private var isResultDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvDisplay = findViewById<TextView>(R.id.tvDisplay)

        // Number buttons
        findViewById<View>(R.id.btn0).setOnClickListener { appendNumber("0") }
        findViewById<View>(R.id.btn1).setOnClickListener { appendNumber("1") }
        findViewById<View>(R.id.btn2).setOnClickListener { appendNumber("2") }
        findViewById<View>(R.id.btn3).setOnClickListener { appendNumber("3") }
        findViewById<View>(R.id.btn4).setOnClickListener { appendNumber("4") }
        findViewById<View>(R.id.btn5).setOnClickListener { appendNumber("5") }
        findViewById<View>(R.id.btn6).setOnClickListener { appendNumber("6") }
        findViewById<View>(R.id.btn7).setOnClickListener { appendNumber("7") }
        findViewById<View>(R.id.btn8).setOnClickListener { appendNumber("8") }
        findViewById<View>(R.id.btn9).setOnClickListener { appendNumber("9") }
        findViewById<View>(R.id.btnDot).setOnClickListener { appendNumber(".") }

        // Clear button
        findViewById<View>(R.id.btnClear).setOnClickListener { clearScreen() }

        // Operation buttons
        findViewById<View>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        findViewById<View>(R.id.btnSubtract).setOnClickListener { setOperator("-") }
        findViewById<View>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<View>(R.id.btnDivide).setOnClickListener { setOperator("/") }

        // Equal button
        findViewById<View>(R.id.btnEqual).setOnClickListener { calculateResult() }
    }

    private fun appendNumber(value: String) {
        if (isOperatorPressed || isResultDisplayed) {
            tvDisplay!!.text = value
            isOperatorPressed = false
            isResultDisplayed = false
        } else {
            val currentText = tvDisplay!!.text.toString()
            if (currentText != "0") {
                tvDisplay!!.text = currentText + value
            } else {
                tvDisplay!!.text = value
            }
        }
    }

    private fun setOperator(op: String) {
        if (operator.isNotEmpty()) {
            calculateResult()
        }
        firstNum = tvDisplay!!.text.toString().toDouble()
        operator = op
        isOperatorPressed = true
    }

    private fun calculateResult() {
        if (operator.isEmpty()) return

        secondNum = tvDisplay!!.text.toString().toDouble()
        val result = when (operator) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "*" -> firstNum * secondNum
            "/" -> firstNum / secondNum
            else -> 0.0
        }
        val df = DecimalFormat("#.##")
        tvDisplay!!.text = df.format(result)
        firstNum = df.format(result).toDouble()
        operator = ""
        isResultDisplayed = true
    }

    private fun clearScreen() {
        tvDisplay?.text = "0"
        firstNum = 0.0
        secondNum = 0.0
        operator = ""
        isOperatorPressed = false
        isResultDisplayed = false
    }
}
