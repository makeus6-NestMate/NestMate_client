package com.example.nm1.src.main.home.nest.todo

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.example.nm1.databinding.DialogTodoCockBinding

class TodoCockDialogFragment : DialogFragment() {
    private lateinit var binding: DialogTodoCockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogTodoCockBinding.inflate(inflater, container, false)
        var familylist = arrayListOf<String>()
        familylist.add("모두에게")
        familylist.add("유리")
        familylist.add("해피")

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, familylist)
        binding.todoCockSpinner.adapter = adapter

        binding.todoBtnCockcancel.setOnClickListener {
            dismiss()
        }
        return binding.root

    }
}