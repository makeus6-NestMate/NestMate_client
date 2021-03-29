package com.example.nm1.src.main.home

import android.os.Bundle
import android.view.View
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTipTwoBinding

class TipTwoFragment : BaseFragment<FragmentTipTwoBinding>(FragmentTipTwoBinding::bind, R.layout.fragment_tip_two){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //first
        binding.tip2Toolbar.toolbarTitle.text="집안일 TIP"
        binding.tip2FirstTitle.setImageResource(R.drawable.tip_ex2)
        binding.tip2FirstDivider.setImageResource(R.drawable.tip_two_divider)
        binding.tip2FirstTxt.text="안녕하세요! 둥지메이트 개발자 ‘파닥몬’이에요!\n" +
                "맞벌이하는 부부들 주목! \n" +
                "혹시 가사 분담 때문에 불만을 가진 적이 있나요? \n" +
                "그렇다면 이 글을 꼭! 읽어주세요."
        binding.tip2FirstWriterImg.setImageResource(R.drawable.tip_two_writer)
        binding.tip2FirstWriterName.text="파닥몬"

        //first
        binding.tip2Fir.tipTitleImg.setImageResource(R.drawable.tip_two_fir_img)
        binding.tip2Fir.tipTitleTxt.text="당신이 식사 준비를 했다면 설거지는 내가 할게!"
        binding.tip2Fir.tipTxt.text="한 명이 요리를 했다면 \n" +
                "다른 한 명은 설거지를 하는 방식으로 해보세요! \n" +
                "식사의 처음과 끝을 나누어 공평하게 해결할 수 있어요! "
        //second
        binding.tip2Sec.tipTitleImg.setImageResource(R.drawable.tip_two_sec_img)
        binding.tip2Sec.tipTitleTxt.text="아침의 1분만 양보하세요!"
        binding.tip2Sec.tipTxt.text="아파트라면 단지 내 분리수거 분류대로, \n" +
                "혹은, 수거일 대상 분류대로 나누어서 쓰레기를 담으세요. \n" +
                "그리고 전날 저녁 봉지를 묶고, \n" +
                "아침 1분만 일찍 나가서 놓기만 하면 끝이랍니다! \n" +
                "우리 담당 날에는 아침 1분만 양보하기로 해요~ "

        //third
        binding.tip2Thr.tipTitleImg.setImageResource(R.drawable.tip_two_thr_img)
        binding.tip2Thr.tipTitleTxt.text="방청소? 쓸기와 닦기로 환상의 조합!"
        binding.tip2Thr.tipTxt.text="방청소를 하려면 먼지를 쓸고 바닥을 닦고… \n" +
                "혼자라면 버겁지만 부부니까 하나씩 맡아서 \n" +
                "해보면 어떨까요? 힘을 합쳐 방청소를 다 했다면 \n" +
                "하이파이브로 서로를 격려해주세요!"

        //last
        binding.tip2LastWriterImg.setImageResource(R.drawable.tip_two_writer)
        binding.tip2LastTxt.text="파닥몬이 알려준 맞벌이 부부의 가사 분담 팁!\n" +
                " 도움이 되셨나요?\n" +
                " 그럼 다음 시간에도 더 좋은 꿀팁으로 찾아올게요~"

        //bottom
        var postDate = "2021.02.22"
        binding.tip2PostDate.text="게시일 : "+postDate
        binding.tip2PostPerson.text="파닥몬"
    }
}