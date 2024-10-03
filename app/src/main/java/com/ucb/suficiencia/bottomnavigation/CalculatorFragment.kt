package com.ucb.suficiencia.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ucb.suficiencia.bottomnavigation.R
import java.text.DecimalFormat

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {
    private var tvDisplay: TextView? = null
    private var firstNum = 0.0
    private var secondNum = 0.0
    private var operator = ""
    private var isOperatorPressed = false
    private var isResultDisplayed = false
    private var hasDecimal = false
    private val decimalFormat = DecimalFormat("#.####") // Format for displaying numbers

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calculator, container, false)
        tvDisplay = view.findViewById(R.id.tvDisplay)

        // Number buttons
        view.findViewById<Button>(R.id.btn0).setOnClickListener { appendNumber("0") }
        view.findViewById<Button>(R.id.btn1).setOnClickListener { appendNumber("1") }
        view.findViewById<Button>(R.id.btn2).setOnClickListener { appendNumber("2") }
        view.findViewById<Button>(R.id.btn3).setOnClickListener { appendNumber("3") }
        view.findViewById<Button>(R.id.btn4).setOnClickListener { appendNumber("4") }
        view.findViewById<Button>(R.id.btn5).setOnClickListener { appendNumber("5") }
        view.findViewById<Button>(R.id.btn6).setOnClickListener { appendNumber("6") }
        view.findViewById<Button>(R.id.btn7).setOnClickListener { appendNumber("7") }
        view.findViewById<Button>(R.id.btn8).setOnClickListener { appendNumber("8") }
        view.findViewById<Button>(R.id.btn9).setOnClickListener { appendNumber("9") }
        view.findViewById<Button>(R.id.btnDot).setOnClickListener { appendDecimal() }

        // Clear button
        view.findViewById<Button>(R.id.btnClear).setOnClickListener { clearScreen() }

        // Operation buttons
        view.findViewById<Button>(R.id.btnAdd).setOnClickListener { setOperator("+") }
        view.findViewById<Button>(R.id.btnSubtract).setOnClickListener { setOperator("-") }
        view.findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        view.findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperator("/") }

        // Equal button
        view.findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }

        return view
    }

    private fun appendNumber(value: String) {
        // Reset display if result was shown
        if (isResultDisplayed) {
            // Allow using the last result as the first number only if no new first number is input
            if (operator.isEmpty()) {
                tvDisplay?.text = value
                firstNum = value.toDoubleOrNull() ?: 0.0 // Update firstNum to the new input
            } else {
                // If an operator was pressed, keep the result as the first number
                tvDisplay?.text = value
            }
            isResultDisplayed = false
            hasDecimal = false // Reset decimal state
            isOperatorPressed = false // Reset operator pressed state
        } else {
            val currentText = tvDisplay?.text.toString()
            // Replace display if an operator was pressed
            if (isOperatorPressed) {
                tvDisplay?.text = value
                isOperatorPressed = false // Reset operator pressed state
            } else {
                // Avoid leading zeros and limit length
                if (currentText.length >= 15) return // Limit input length
                if (currentText == "0") {
                    tvDisplay?.text = value
                } else {
                    tvDisplay?.text = currentText + value
                }
            }
        }
    }

    private fun appendDecimal() {
        val currentText = tvDisplay?.text.toString()
        if (!hasDecimal) {
            if (currentText.isEmpty() || isOperatorPressed || isResultDisplayed) {
                tvDisplay?.text = "0."
            } else {
                tvDisplay?.text = currentText + "."
            }
            hasDecimal = true
        }
    }

    private fun setOperator(op: String) {
        val currentText = tvDisplay?.text.toString()
        if (currentText.isEmpty() || currentText == ".") return // Prevent operator if no number is input

        // Calculate previous operation if an operator is pressed again
        if (operator.isNotEmpty() && !isResultDisplayed) {
            calculateResult()
        }

        // Store first number
        firstNum = if (isResultDisplayed) firstNum else currentText.toDoubleOrNull() ?: 0.0
        operator = op
        isOperatorPressed = true
        hasDecimal = false // Reset decimal state for next input
    }

    private fun calculateResult() {
        if (operator.isEmpty()) return

        secondNum = tvDisplay?.text.toString().toDoubleOrNull() ?: 0.0
        val result = when (operator) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "*" -> firstNum * secondNum
            "/" -> {
                if (secondNum == 0.0) {
                    clearScreen() // Clear on division by zero
                    tvDisplay?.text = "Error"
                    return
                } else {
                    firstNum / secondNum
                }
            }
            else -> 0.0
        }

        // Display the result
        tvDisplay?.text = decimalFormat.format(result)
        firstNum = result // Save the result for further calculations
        operator = "" // Reset operator for the next calculation
        isResultDisplayed = true
        hasDecimal = tvDisplay?.text?.contains(".") ?: false // Retain decimal state for next input
    }

    private fun clearScreen() {
        tvDisplay?.text = "0"
        firstNum = 0.0
        secondNum = 0.0
        operator = ""
        isOperatorPressed = false
        isResultDisplayed = false
        hasDecimal = false
    }
}
