package com.example.nm1.src.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTipTwoBinding

class TipTwoFragment : BaseFragment<FragmentTipTwoBinding>(FragmentTipTwoBinding::bind, R.layout.fragment_tip_two){

    private lateinit var callback: OnBackPressedCallback
    @SuppressLint("SetTextI18n")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //first
        binding.tipToolbar.toolbarTitle.text="집안일 TIP"
        binding.tipFirstTitle.setImageResource(R.drawable.tip_ex2)
        binding.tipFirstDivider.setImageResource(R.drawable.tip_two_divider)
        binding.tipFirstTxt.text="안녕하세요! 둥지메이트 개발자 ‘파닥몬’이에요!\n" +
                "맞벌이하는 부부들 주목! \n" +
                "혹시 가사 분담 때문에 불만을 가진 적이 있나요? \n" +
                "그렇다면 이 글을 꼭! 읽어주세요."
        binding.tipFirstWriterImg.setImageResource(R.drawable.tip_two_writer)
        binding.tipFirstWriterName.text="파닥몬"

        //first
        binding.tipFir.tipTitleImg.setImageResource(R.drawable.tip_two_fir_img)
        binding.tipFir.tipTitleTxt.text="당신이 식사 준비를 했다면 설거지는 내가 할게!"
        binding.tipFir.tipTxt.text="한 명이 요리를 했다면 \n" +
                "다른 한 명은 설거지를 하는 방식으로 해보세요! \n" +
                "식사의 처음과 끝을 나누어 공평하게 해결할 수 있어요! "
        //second
        binding.tipSec.tipTitleImg.setImageResource(R.drawable.tip_two_sec_img)
        binding.tipSec.tipTitleTxt.text="아침의 1분만 양보하세요!"
        binding.tipSec.tipTxt.text="아파트라면 단지 내 분리수거 분류대로, \n" +
                "혹은, 수거일 대상 분류대로 나누어서 쓰레기를 담으세요. \n" +
                "그리고 전날 저녁 봉지를 묶고, \n" +
                "아침 1분만 일찍 나가서 놓기만 하면 끝이랍니다! \n" +
                "우리 담당 날에는 아침 1분만 양보하기로 해요~ "

        //third
        binding.tipThr.tipTitleImg.setImageResource(R.drawable.tip_two_thr_img)
        binding.tipThr.tipTitleTxt.text="방청소? 쓸기와 닦기로 환상의 조합!"
        binding.tipThr.tipTxt.text="방청소를 하려면 먼지를 쓸고 바닥을 닦고… \n" +
                "혼자라면 버겁지만 부부니까 하나씩 맡아서 \n" +
                "해보면 어떨까요? 힘을 합쳐 방청소를 다 했다면 \n" +
                "하이파이브로 서로를 격려해주세요!"

        //last
        binding.tipLastWriterImg.setImageResource(R.drawable.tip_two_writer)
        binding.tipLastTxt.text="파닥몬이 알려준 맞벌이 부부의 가사 분담 팁!\n" +
                " 도움이 되셨나요?\n" +
                " 그럼 다음 시간에도 더 좋은 꿀팁으로 찾아올게요~"

        //bottom
        var postDate = "2021.02.22"
        binding.tipPostDate.text="게시일 : "+postDate
        binding.tipPostPerson.text="작성자 : 파닥몬"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@TipTwoFragment)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}