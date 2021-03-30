package com.nestmate.nm1.src.main.home.nest

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.nestmate.nm1.PagerFragmentStateAdapter
import com.nestmate.nm1.R
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.databinding.ViewpagerNestTabBinding
import com.nestmate.nm1.src.main.home.nest.member.MemFragment
import com.nestmate.nm1.src.main.home.nest.todo.TodoFragment
import com.nestmate.nm1.src.main.home.nest.notice.NVFragment
import com.nestmate.nm1.src.main.home.nest.memo.MemoFragment
import com.nestmate.nm1.src.main.home.nest.rule.RuleFragment
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : BaseFragment<ViewpagerNestTabBinding>(ViewpagerNestTabBinding::bind,
    R.layout.viewpager_nest_tab) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = PagerFragmentStateAdapter(requireActivity())
        // 3개의 Fragment Add
        pagerAdapter.addFragment(TodoFragment())
        pagerAdapter.addFragment(MemoFragment())
        pagerAdapter.addFragment(RuleFragment())
        pagerAdapter.addFragment(NVFragment())
        pagerAdapter.addFragment(MemFragment())
        // Adapter
        binding.vpInner.adapter = pagerAdapter

        binding.vpInner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        val tabTextArray = arrayOf("할일", "메모", "규칙", "공지/투표", "멤버")
        // TabLayout attach
        TabLayoutMediator(binding.nestTabTop, binding.vpInner) { tab, position ->
            tab.text = tabTextArray[position]
        }.attach()

        binding.vpInner.isUserInputEnabled = false
    }
}