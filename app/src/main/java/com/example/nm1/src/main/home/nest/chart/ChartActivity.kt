package com.example.nm1.src.main.home.nest.chart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityChartBinding
import com.example.nm1.src.main.home.nest.chart.model.*
import kotlinx.android.synthetic.main.activity_chart.*
import kotlinx.android.synthetic.main.activity_chart.view.*
import kotlinx.android.synthetic.main.activity_nest.*
import kotlinx.android.synthetic.main.chart_day.view.*

class ChartActivity : BaseActivity<ActivityChartBinding>(ActivityChartBinding::inflate),
    ChartActivityView {
    val Int.dp: Int
        get() {
            val metrics = resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), metrics).toInt()
        }

    private lateinit var dayList : List<View>
    private lateinit var memList : List<ChartMemberInfo>
    private lateinit var dataList : List<List<ChartDataInfo>>
    private lateinit var bestMember : ChartBestInfo

    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    val colorArray = arrayOf("#5e6af5", "#43a8ff", "#21ffbb", "#cfff2e","#ffe033","#ffb221","#ff7f21","#ff2121","#b70d0d")
    val txtArray= arrayOf("월", "화","수","목","금","토","일")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog(this)
        ChartService(this).tryGetChart(roomId)

        dayList =
            arrayOf(chart_mon, chart_tue, chart_wed, chart_thu, chart_fri, chart_sat, chart_sun).toList()
        for(idx1 in dayList.indices) init(dayList[idx1], txtArray[idx1])

        binding.chartToolbar.toolbarTitle.text = "차트"

        binding.clapBtn.setOnClickListener {
            // bestMember 이게 맞나...?
            ChartService(this).tryPostChartClap(roomId, bestMember.userId)
        }
    }

    fun init(day : View, txt: String) {
        day.what_day_txt.text=txt
    }

    fun draw(day : View, color: String, cnt : Int, mx:Int){
        if(cnt==0) return
        val img = ImageView(day.context)
        val len = day.chart_block.height
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (len*cnt/mx).dp)
        layoutParams.setMargins(0.dp,4.dp,0.dp,0.dp)
        img.layoutParams=layoutParams
        val drawable = resources.getDrawable(R.drawable.chart_rec_design)
        drawable.setTint(Color.parseColor(color))
        img.setImageDrawable(drawable)
        day.chart_block.addView(img)
    }

    override fun onPostChartClapSuccess(response: PostChartClapResponse) {
        ChartDialog().show(supportFragmentManager, "ChartDialog")
    }

    override fun onPostChartClapFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetChartSuccess(response: GetChartResponse) {
        memList = response.result.member
        dataList = response.result.chart
        bestMember = response.result.bestMember

        if(bestMember!=null){
            binding.chartBestMate.chart_best_mate_nothing.visibility=View.INVISIBLE
            binding.chartBestMate.chart_best_mate_existing.visibility=View.VISIBLE
            if(bestMember.profileImg!=null) Glide.with(this).load(bestMember.profileImg).into(binding.chartBestMate.chart_best_mate_existing.bestmate_img)
            else binding.chartBestMate.chart_best_mate_existing.bestmate_img.setImageResource(R.drawable.home_bird_icon)
            binding.chartBestMate.chart_best_mate_existing.bestmate_name_tv.text= bestMember.nickname
            binding.chartBestMate.chart_best_mate_existing.bestmate_cnt_tv.text= "횟수 "+bestMember.prizeCount.toString()+"회"
        }

        binding.chartPersonList.adapter= ChartMemberAdapter(this, memList)

        var mx=0
        for(idx1 in dayList.indices) {
            var tmp=0
            for(idx2 in dataList[idx1].indices){
                tmp+=dataList[idx1][idx2].count
            }
            if(mx<tmp) mx=tmp
        }

        for(idx1 in dayList.indices) {
            init(dayList[idx1], txtArray[idx1])
            for(idx2 in dataList[idx1].indices) draw(dayList[idx1], colorArray[idx2], dataList[idx1][idx2].count, mx)
        }

        dismissLoadingDialog()
    }

    override fun onGetChartFailure(message: String) {
        TODO("Not yet implemented")
    }
}