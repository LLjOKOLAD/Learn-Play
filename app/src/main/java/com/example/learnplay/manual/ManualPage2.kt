package com.example.learnplay.manual

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.R
import com.zanvent.mathview.MathView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ManualPage2 : AppCompatActivity() {

    private fun setupMathViews(){

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_page2)



        GlobalScope.launch(Dispatchers.Main) {
            val mathView1: MathView = findViewById(R.id.manual_mathView_2_1)
            mathView1.setTextSize(16)
            mathView1.textColor = "#FFFFFF"

            mathView1.text =
                "Степенью числа \$а\$ с натуральным показателем \$n\$ , большим 1, называется произведение \$n\$ множителей, каждый из которых равен \$а\$.<br>" +
                        "\$a^n={a·a·a·a·a}↙{n, \\раз}\$"
        }
        GlobalScope.launch(Dispatchers.Main) {
            val mathView2: MathView = findViewById(R.id.manual_mathView_2_2)
            mathView2.setTextSize(16)
            mathView2.textColor = "#FFFFFF"

            mathView2.text =
                "<p>В частном случае основание \$а\$ с показателем \$1\$ называется само число \$а\$.</p>\n" +
                        "<p>\$a^1=a\$</p>\n" +
                        "<br>" +
                        "<p>Степень с отрицательным основанием и чётным показателем равна степени с основанием, противоположным исходному основанию, и с тем же показателем.</p>\n" +
                        "<p>\$(-a)^{2n}=a^{2n}\$, где \$2n\$ - четный показатель</p>\n" +
                        "<p>Основание в любом отрицательном показателе степени можно представить в виде основания в таком же положительном показателе степени, изменив положение основания относительно черты дроби.</p>\n" +
                        "<p>\$a^{-n}={1}/{a^n}\$</p>\n" +
                        "<p>\${a^{ -n }}/{b^{-k}}={b^k}/{a^n}\$</p>"
        }

        GlobalScope.launch(Dispatchers.Main) {
            val mathView3: MathView = findViewById(R.id.manual_mathView_2_3)
            mathView3.setTextSize(16)
            mathView3.textColor = "#000000"

            mathView3.text = "<p>\$2^{-2}={1}/{2^2}={1}/{4}=0.25\$</p>" +
                    "<p>Радикал (корень) можно представить в виде степени с дробным показателем</p>" +
                    "<p>\$√^n{a^k}=a^{{k}/{n}}\$</p><p>\$√^3{3}=3^{{1}/{3}}\$</p>"
        }

        GlobalScope.launch(Dispatchers.Main) {
            val mathView4: MathView = findViewById(R.id.manual_mathView_2_4)
            mathView4.setTextSize(16)
            mathView4.textColor = "#FFFFFF"

            mathView4.text = "<p>Свойства степеней<br /></p>\n" +
                    "<p>1. При умножении степеней с одинаковыми основаниями основание остается прежним, а показатели складываются.</p>\n" +
                    "<p>\$a^n·a^m=a^{n+m}\$</p>\n" +
                    "<p>2. При делении степеней с одинаковыми основаниями основание остается прежним, а показатели вычитаются<br /></p>\n" +
                    "<p>\$a^n:a^m=a^{n-m}\$</p>\n" +
                    "<p>3. При возведении степени в степень основание остается прежним, а показатели перемножаются<br /></p>\n" +
                    "<p>\$(a^n)^m=a^{n·m}\$</p>\n" +
                    "<p>4. При возведении в степень произведения в эту степень возводится каждый множитель><br /></p>\n" +
                    "<p>\$(a·b)^n=a^n·b^n\$</p>\n" +
                    "<p>5. При возведении в степень дроби в эту степень возводиться числитель и знаменатель</p>\n" +
                    "<p>\$({a}/{b})^n={a^n}/{b^n}\$</p>\n" +
                    "<p>6. При возведении любого основания в нулевой показатель степени результат равен единице</p>\n" +
                    "<p>\$a^0=1\$</p>"
        }

        GlobalScope.launch(Dispatchers.Main) {
            val mathView5: MathView = findViewById(R.id.manual_mathView_2_5)
            mathView5.setTextSize(16)
            mathView5.textColor = "#000000"

            mathView5.text = "<p>Вычислить \${2^8·(7^2)^4}/{14^7}\$</p>" +
                    "<p>Решение:</p><p>Перед решением необходимо сделать одинаковые основания у степеней, " +
                    "для этого разложим основание \$14\$ на множители.</p>" +
                    "<p>\${2^8·(7^2)^4}/{14^7} ={2^8·(7^2)^4}/{7^7·2^7}\$</p>" +
                    "<p>Далее применим свойства степеней</p><p>\${2^8·(7^2)^4}/{7^7·2^7}={2^8·7^8}/{7^7·2^7}=2^{8-7}·7^{8-7}=2·7=14\$</p>" +
                    "<p>Ответ: \$14\$</p><p><br /></p><p>Найдите значение выражения: \${(a^{ √6 })^{ 2√6 }}/{a^{13}}\$ при \$a=5\$</p>" +
                    "<p>Решение:</p>" +
                    "<p>Для начала упростим выражение, используя свойства степеней</p>" +
                    "<p>\${(a^{ √6 })^{ 2√6 }}/{a^{13}} ={a^{√6·2√6}}/{a^{13}} ={a^{12}}/{a^{13}} =a^{-1}={1}/{a}\$</p>" +
                    "<p>Подставим в полученное выражение вместо «а» число \$5\$.</p>" +
                    "<p>${1}/{a}={1}/{5}=0,2\$</p><p>Ответ: \$0,2\$</p>"
        }


    }
}