package com.example.nm1.src.main.home.nest

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityChartBinding
import kotlinx.android.synthetic.main.activity_chart.*
import kotlinx.android.synthetic.main.chart_day.view.*
import kotlinx.android.synthetic.main.toolbar_back.*

class ChartActivity : BaseActivity<ActivityChartBinding>(ActivityChartBinding::inflate) {
    val Int.dp: Int
        get() {
            val metrics = resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
        }


    val colorArray = arrayOf("#5e6af5", "#43a8ff", "#21ffbb", "#cfff2e")
    val txtArray= arrayOf("월", "화","수","목","금","토","일")
    val dataArray= arrayListOf<List<Int>>(listOf(20,30,40,50), listOf(30,10,30,50),listOf(20,50,40,30),listOf(20,30,40,50),listOf(20,30,40,50),listOf(20,30,40,50),listOf(20,30,40,50))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dayArray = arrayOf(chart_mon, chart_tue, chart_wed, chart_thu, chart_fri, chart_sat, chart_sun)

        binding.chartToolbar.toolbarTitle.text = "차트"
        for(idx1 in dayArray.indices) {
            init(dayArray[idx1], txtArray[idx1])
            for (idx2 in dataArray[idx1].indices) draw(
                dayArray[idx1],
                colorArray[idx2],
                dataArray[idx1][idx2]
            )
        }

        clap_btn.setOnClickListener {
            ChartDialog().show(supportFragmentManager, "ChartDialog")
        }
        back_btn.setOnClickListener {
            finish()
        }
    }

    fun init(day : View, txt: String) {
        day.what_day_txt.text=txt
    }

    fun draw(day : View, color: String, cnt : Int){
        val img = ImageView(day.context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, cnt.dp)
        layoutParams.setMargins(0.dp,4.dp,0.dp,0.dp)
        img.layoutParams=layoutParams
        img.setBackgroundColor(Color.parseColor(color))
        day.chart_block.addView(img)
    }
}