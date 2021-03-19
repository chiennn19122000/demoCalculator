package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private var numberA: String = "0"
    private var numberB: String = "0"
    private var operator: String = ""
    private var result: String = ""
    private val numbers = hashSetOf<String>("0","1","2","3","4","5","6","7","8","9")
    private val operators = hashSetOf<String>("+","-","x",":")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOf<View>(number_0,number_1,number_2,number_3,number_4,number_5,number_6,number_7,number_8,number_9,
        equal,add,sub,mul,div,ac).forEach { View -> View?.setOnClickListener(this) }
    }

    override fun onClick(v: View?) {
        val click = if(v is AppCompatButton) v else return
        val value = click.text.toString()
        var content: String = textContent.text.toString()
        when{
            checkNumber(value) -> {
                if (numberA.equals("0")) {
                    numberA = value
                    content = ""
                }
                textContent.text = content + value
            }
            checkOperator(value) -> {
                if ( operators.contains(content.get(content.length - 1).toString())) content = content.substring(0,content.length-1)
                numberA = if (content.isNullOrEmpty()) "0" else content
                operator = value
                textContent.text = content + operator
            }
            value == "=" -> {
                if (operator.equals("") ) textContent.text = content + "\n=" + content
                else {
                    numberB = if (numberA.length + 1 == content.length) "0" else content.substring(numberA.length + 1)
                    textContent.text = content + "\n=" + takeEqual(numberA.toDouble(),numberB.toDouble())
                }
                numberA = "0"
                numberB = "0"
                operator = ""
            }
            value == "ac" -> {
                content = "0"
                textContent.text = content
                numberA = "0"
                numberB = "0"
                operator = ""
            }
        }
    }
    private fun checkNumber(value: String): Boolean = numbers.contains(value)
    private fun checkOperator(value: String): Boolean = operators.contains(value)
    private fun takeEqual(value1: Double,value2: Double) = when(operator)
    {
        ":" -> {
            if (value2 == 0.00) "lỗi"
            else value1 / value2
        }
        "-" -> value1 - value2
        "x" -> value1 * value2
        else -> value1 + value2
    }
}
