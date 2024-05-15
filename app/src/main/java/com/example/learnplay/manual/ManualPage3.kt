package com.example.learnplay.manual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.R
import com.zanvent.mathview.MathView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ManualPage3 : AppCompatActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_page3)


        GlobalScope.launch(Dispatchers.Main) {
            val mathView1: MathView = findViewById(R.id.manual_mathView_3_1)
            mathView1.setTextSize(16)
            mathView1.textColor = "#FFFFFF"

            mathView1.text =
                "<b>Признаки равенства треугольников</b><br>\n" + "\n" +
                        "&emsp;1. Если две стороны и угол между ними одного треугольника соответственно равны двум сторонам и углу между ними другого треугольника, то треугольники равны.<br>\n" + "\n" +
                        "&emsp;2. Если сторона и два прилежащих к ней угла одного треугольника соответственно равны стороне и двум прилежащим к ней углам другого треугольника, то треугольники равны.<br>\n" + "\n" +
                        "&emsp;3. Если три стороны одного треугольника соответственно равны трём сторонам другого треугольника, то треугольники равны.<br><br>\n" + "\n" +
                        "&emsp;<b>Признаки равенства прямоугольных треугольников</b><br>\n" + "\n" +
                        "&emsp;1. По двум катетам.<br>\n" + "\n" +
                        "&emsp;2. По катету и гипотенузе.<br>\n" + "\n" +
                        "&emsp;3. По гипотенузе и острому углу<br>\n" + "\n" +
                        "&emsp;4. По катету и острому углу<br><br>\n" + "\n" +
                        "&emsp;<b>Теорема о сумме углов треугольника и следствия из неё</b><br>\n" + "\n" +
                        "&emsp;1. Сумма внутренних углов треугольника равна 180&#176;.<br>\n" + "\n" +
                        "&emsp;2. Внешний угол треугольника равен сумме двух внутренних не смежных с ним углов.<br>\n" + "\n" +
                        "&emsp;3. Сумма внутренних углов выпуклого n-угольника равна 180&#176;<i>(n − 2)</i>.<br>\n" + "\n" +
                        "&emsp;4. Сумма внешних углов n-угольника равна 360&#176;<br>\n" + "\n" +
                        "&emsp;5. Углы со взаимно перпендикулярными сторонами равны, если они оба острые или оба тупые.<br>\n" + "\n" +
                        "&emsp;6. Угол между биссектрисами смежных углов равен 90&#176;.<br>\n" + "\n" +
                        "&emsp;7. Биссектрисы внутренних односторонних углов при параллельных прямых и секущей перпендикулярны.<br><br>\n" + "\n" +
                        "&emsp;<b>Основные свойства и признаки равнобедренного треугольника</b><br>\n" + "\n" +
                        "&emsp;1. Углы при основании равнобедренного треугольника равны.<br>\n" + "\n" +
                        "&emsp;2. Если два угла треугольника равны, то он равнобедренный.<br>\n" + "\n" +
                        "&emsp;3. В равнобедренном треугольнике медиана, биссектриса и высота, проведённые к основанию, совпадают.<br>\n" + "\n" +
                        "&emsp;4. Если в треугольнике совпадает любая пара отрезков из тройки: медиана, биссектриса, высота, — то он является равнобедренным.<br><br>\n" + "\n" +
                        "&emsp;<b>Неравенство треугольника и следствия из него</b><br>\n" + "\n" +
                        "&emsp;1. Сумма двух сторон треугольника больше его третьей стороны<br>\n" + "\n" +
                        "&emsp;2. Сумма звеньев ломаной больше отрезка, соединяющего начало первого звена с концом последнего.<br>\n" + "\n" +
                        "&emsp;3. Против большего угла треугольника лежит бOльшая сторона.<br>\n" + "\n" +
                        "&emsp;4. Против большей стороны треугольника лежит больший угол.<br>\n" + "\n" +
                        "&emsp;5. Гипотенуза прямоугольного треугольника больше катета<br>\n" + "\n" +
                        "&emsp;6. Если из одной точки проведены к прямой перпендикуляр и наклонные, то<br>\n" + "\n" +
                        "&emsp; 1) перпендикуляр короче наклонных;<br>\n" + "\n" +
                        "&emsp; 2) большей наклонной соответствует большая ´ проекция и наоборот<br><br>\n" + "\n" +
                        "&emsp;<b>Средняя линия треугольника.</b>\n" + "\n" +
                        "Отрезок, соединяющий середины двух сторон треугольника, называется средней линией треугольника.<br><br>\n" + "\n" +
                        "&emsp;<b>Теорема о средней линии треугольника.</b>\n" + "\n" +
                        "Средняя линия треугольника параллельна стороне треугольника и равна её половине.<br><br>\n" + "\n" +
                        "&emsp;<b>Теоремы о медианах треугольника</b><br>\n" + "\n" +
                        "&emsp;1. Медианы треугольника пересекаются в одной точке и делятся ею в отношении 2 : 1, считая от вершины.<br>\n" + "\n" +
                        "&emsp;2. Если медиана треугольника равна половине стороны, к которой она проведена, то треугольник прямоугольный<br>\n" + "\n" +
                        "&emsp;3. Медиана прямоугольного треугольника, проведённая из вершины прямого угла, равна половине гипотенузы<br><br>\n" + "\n" +
                        "&emsp;<b>Свойство серединных перпендикуляров к сторонам треугольника.</b>\n" + "\n" +
                        "Серединные перпендикуляры к сторонам треугольника пересекаются в одной точке, которая является центром окружности, описанной около треугольника<br><br>\n" + "\n" +
                        "&emsp;<b>Теорема о высотах треугольника.</b>\n" + "\n" +
                        "Прямые, содержащие высоты треугольника, пересекаются в одной точке.<br><br>\n" + "\n" +
                        "&emsp;<b>Теорема о биссектрисах треугольника. </b>\n" + "\n" +
                        "Биссектрисы треугольника пересекаются в одной точке, которая является центром окружности, вписанной в треугольник.<br><br>\n" + "\n" +
                        "&emsp;<b>Свойство биссектрисы треугольника.</b>\n" + "\n" +
                        "Биссектриса треугольника делит его сторону на отрезки, пропорциональные двум другим сторонам<br><br>" +


                        "<b>Признаки подобия треугольников</b><br>\n" + "\n" +
                        "&emsp;1. Если два угла одного треугольника соответственно равны двум углам другого, то треугольники подобны.<br>\n" + "\n" +
                        "&emsp;2. Если две стороны одного треугольника соответственно пропорциональны двум сторонам другого, а углы, заключенные между этими сторонами, равны, то треугольники подобны.<br>\n" + "\n" +
                        "&emsp;3. Если три стороны одного треугольника соответственно пропорциональны трём сторонам другого, то треугольники подобны.<br><br>\n" + "\n" +
                        "&emsp;<b>Площади подобных треугольников</b><br>\n" + "\n" +
                        "&emsp;1. Отношение площадей подобных треугольников равно квадрату коэффициента подобия.<br>\n" + "\n" +
                        "&emsp;2. Если два треугольника имеют равные углы, то их площади относятся как произведения сторон, заключающих эти углы.<br><br>\n" + "\n" +
                        "&emsp;<b>В прямоугольном треугольнике</b><br>\n" + "\n" +
                        "&emsp;1. Катет прямоугольного треугольника равен произведению гипотенузы на синус противолежащего или на косинус прилежащего к этому катету острого угла.<br>\n" + "\n" +
                        "&emsp;2. Катет прямоугольного треугольника равен другому катету, умноженному на тангенс противолежащего или на котангенс прилежащего к этому катету острого угла.<br>\n" + "\n" +
                        "&emsp;3. Катет прямоугольного треугольника, лежащий против угла в 30&#176;, равен половине гипотенузы.<br>\n" + "\n" +
                        "&emsp;4. Если катет прямоугольного треугольника равен половине гипотенузы, то угол, противолежащий этому катету, равен 30&#176;.<br>" +
                        "<br>\$R={c}/{2};r={a+b-c}/{2}=p-c\$<br>"+
                        "где <i>a, b</i> — катеты, а <i>c</i> — гипотенуза прямоугольного треугольника; <i>r и R</i> — радиусы вписанной и описанной окружностей соответственно<br><br>" +


                        "<b>Теорема Пифагора и теорема, обратная теореме Пифагора</b><br>\n" + "\n" +
                        "&emsp;1. Квадрат гипотенузы прямоугольного треугольника равен сумме квадратов катетов.<br>\n" + "\n" +
                        "&emsp;2. Если квадрат стороны треугольника равен сумме квадратов двух других его сторон, то треугольник — прямоугольный<br><br>\n" + "\n" +
                        "&emsp;<b>Средние пропорциональные в прямоугольном треугольнике</b><br>\n" + "\n" +
                        "&emsp;Высота прямоугольного треугольника, проведённая из вершины прямого угла, есть среднее пропорциональное проекций катетов на гипотенузу, а каждый катет есть среднее пропорциональное гипотенузы и своей проекции на гипотенузу<br><br>\n" + "\n" +
                        "&emsp;<b>Метрические соотношения в треугольнике</b><br>\n" + "\n" +
                        "&emsp;1. <b>Теорема косинусов.</b> Квадрат стороны треугольника равен сумме квадратов двух других сторон без удвоенного произведения этих сторон на косинус угла между ними.<br>\n" + "\n" +
                        "&emsp;2. <b>Следствие из теоремы косинусов.</b> Сумма квадратов диагоналей параллелограмма равна сумме квадратов всех его сторон.<br>\n" + "\n" +
                        "&emsp;3. <b>Формула для медианы треугольника.</b> Если m — медиана треугольника, проведённая к стороне c, то<br>"+
                        "\$m=1/2√{2a^2+2b^2-c^2}\$" +
                        "&emsp;где a и b — остальные стороны треугольника.<br>\n" + "\n" +
                        "&emsp;4. <b>Теорема синусов.</b> Стороны треугольника пропорциональны синусам противолежащих углов.<br>\n" + "\n" +
                        "&emsp;5. <b>Обобщённая теорема синусов.</b> Отношение стороны треугольника к синусу противолежащего угла равно диаметру окружности, описанной около треугольника.<br><br>"

            val mathView2 = findViewById<MathView>(R.id.manual_mathView_3_2)
            mathView2.setTextSize(16)
            mathView2.textColor = "#FFFFFF"
            mathView2.text = ""

        }
    }
}