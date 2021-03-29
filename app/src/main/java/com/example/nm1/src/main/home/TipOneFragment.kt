package com.example.nm1.src.main.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentTipOneBinding
import com.example.nm1.src.main.MainActivity
import kotlinx.android.synthetic.main.fragment_tip_one.*
import kotlinx.android.synthetic.main.fragment_tip_one.view.*
import kotlinx.android.synthetic.main.tip_one_layout.view.*

class TipOneFragment : BaseFragment<FragmentTipOneBinding> (FragmentTipOneBinding::bind, R.layout.fragment_tip_one){

    private lateinit var callback: OnBackPressedCallback
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //first
        binding.tipToolbar.toolbarTitle.text="집안일 TIP"
        binding.tipFirstTitle.setImageResource(R.drawable.tip_ex)
        binding.tipFirstDivider.setImageResource(R.drawable.tip_one_divider)
        binding.tipFirstTxt.text="안녕하세요. 둥지메이트의 디자이너 해피에요!\n" +
                "지금부터 저희 둥지메이트를 효율적으로 사용할 수 있는 \n" +
                "꿀팁을 알려드릴거에요."
        binding.tipFirstWriterImg.setImageResource(R.drawable.tip_one_writer)
        binding.tipFirstWriterName.text="해피"

        //first
        binding.tipFir.tipTitleImg.setImageResource(R.drawable.tip_one_first)
        tip_fir.tip_title_txt.text="첫번째"
        binding.tipFir.tipTxt.text="할일을 설정할땐 가족 구성원 누구나 알아볼 수 있도록 \n" +
                "상세히 기입해주세요!\n" +
                "자칫 다른 집안일과 착각할 수도 있으니까요!"
        binding.tipFir.tipImg.setImageResource(R.drawable.tip_one_first_img)
        //second
        binding.tipSec.tipTitleImg.setImageResource(R.drawable.tip_one_second)
        binding.tipSec.tipTitleTxt.text="두번째!"
        binding.tipSec.tipTxt.text="설정 시간이 지나도 아무도 할 일을 \n" +
                "수행하지 않았을땐 가족 구성원들을 콕 찔러주세요!"
        binding.tipSec.tipImg.setImageResource(R.drawable.tip_one_second_img)

        //third
        binding.tipThr.tipTitleImg.setImageResource(R.drawable.tip_one_third)
        binding.tipThr.tipTitleTxt.text="세번째!"
        binding.tipThr.tipTxt.text="규칙을 설정할때 너무 많은 규칙은 피해주세요!\n" +
                "규칙을 세우는것은 좋지만 너무 많은 규칙이 생기면\n" +
                " 오히려 갈등이 생길수도 있어요!"
        binding.tipThr.tipImg.setImageResource(R.drawable.tip_one_third_img)

        //fourth
        binding.tipFour.tipTitleImg.setImageResource(R.drawable.tip_one_fourth)
        binding.tipFour.tipTitleTxt.text="네번째!"
        binding.tipFour.tipTxt.text="규칙은 가족 구성원 모두와 상의 후 설정해주세요.\n" +
                "맛있는 음식을 먹으면서, 혹은 다같이 모여있을때 \n" +
                "규칙을 의논하고 입력해보세요.\n" +
                "규칙은 모두가 지키고, 수행하는 일이니까요."

        //fifth
        binding.tipFif.tipTitleImg.setImageResource(R.drawable.tip_one_fourth)
        binding.tipFif.tipTitleTxt.text="마지막!"
        binding.tipFif.tipTxt.text="차트를 통해 이번주 가장 많은 집안일을 \n" +
                "한 최고의 메이트에게 박수를 보내보세요!\n" +
                "우리집이 행복할 수 있었던건 최고의 메이트가 \n" +
                "집안일에 많은 노력을 했기 때문이니까요!"
        binding.tipFif.tipImg.setImageResource(R.drawable.tip_one_fifth_img)

        //last
        binding.tipLastWriterImg.setImageResource(R.drawable.tip_one_writer)
        binding.tipLastTxt.text="이상으로 둥지메이트의 디자이너 해피였습니다!\n" +
                " 다들 행복한 하루되세요 :)"

        //bottom
        binding.tipPostDate.text="게시일 : "+"2021.02.22"
        binding.tipPostPerson.text="작성자 : 해피"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.beginTransaction()?.remove(this@TipOneFragment)?.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}