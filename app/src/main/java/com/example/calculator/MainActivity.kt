package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private var numberA = NUMBER_0
    private var numberB = NUMBER_0
    private var operator = OPERATOR
    private val numbers = hashSetOf<String>(NUMBER_0, NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5, NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9)
    private val operators = hashSetOf<String>(OPERATOR_ADD, OPERATOR_SUB, OPERATOR_MUL, OPERATOR_DIV)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOf<View>(buttonNumber0,buttonNumber1,buttonNumber2,buttonNumber3,buttonNumber4,buttonNumber5,buttonNumber6,buttonNumber7,buttonNumber8,buttonNumber9,
        buttonEqual,buttonAdd,buttonSub,buttonMul,buttonDiv,buttonAc).forEach { View -> View?.setOnClickListener(this) }
    }

    override fun onClick(v: View?) {
        val click = if(v is AppCompatButton) v else return
        val value = click.text.toString()
        var content: String = textContent.text.toString()
        when{
            isNumber(value) -> {
                if (numberA.equals("0")) {
                    numberA = value
                    content = ""
                }
                textContent.text = content + value
            }
            isOperator(value) -> {
                if ( operators.contains(content.get(content.length - 1).toString())) content = content.substring(0,content.length-1)
                numberA = if (content.isNullOrEmpty()) "0" else content
                operator = value
                textContent.text = content + operator
            }
            value == OPERATOR_EQUAL -> {
                if (operator.equals("") ) textContent.text = content + "\n=" + content
                else {
                    numberB = if (numberA.length + 1 == content.length) "0" else content.substring(numberA.length + 1)
                    textContent.text = content + "\n=" + calculate(numberA.toDouble(),numberB.toDouble())
                }
                numberA = NUMBER_0
                numberB = NUMBER_0
                operator = OPERATOR
            }
            value == OPERATOR_AC -> {
                content = "0"
                textContent.text = content
                numberA = NUMBER_0
                numberB = NUMBER_0
                operator = ""
            }
        }
    }
    private fun isNumber(value: String): Boolean = numbers.contains(value)
    private fun isOperator(value: String): Boolean = operators.contains(value)
    private fun calculate(value1: Double, value2: Double) = when(operator)
    {
        ":" -> if (value2 == 0.00) "lỗi" else value1 / value2
        "-" -> value1 - value2
        "x" -> value1 * value2
        else -> value1 + value2
    }
}
