package com.example.learnplay.manual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.R
import com.zanvent.mathview.MathView

class ManualPage1 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_page1)
        val mathView: MathView = findViewById(R.id.manual_mathView_1)

        mathView.setTextSize(20)
        mathView.textColor = "#FFFFFF"

        mathView.text = "<b>Классическое определение вероятности</b><br>\n" +
                "Вероятностью события A называется отношение числа благоприятных\n" +
                "для A исходов к числу всех равновозможных исходов:<br>\n" +
                "\$P(A) = {m}/{n}\$<br>\n" +
                "где <i>n</i> — общее число равновозможных исходов, <i>m</i> — число исходов, благоприятствующих событию A.<br><br>\n" +
                "<b>Противоположные события</b><br>\n" +
                "Событие, противоположное событию A, обозначают &#256;. При проведении испытания всегда происходит ровно одно из двух противоположных событий и<br>\n" +
                "\$P(A) + P(&#256) = 1\$<br>\$P(&#256) = 1 - P(A)\$<br>"


    }


}