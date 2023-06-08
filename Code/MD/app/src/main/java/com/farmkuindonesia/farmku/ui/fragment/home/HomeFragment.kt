package com.farmkuindonesia.farmku.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.FragmentHomeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListNewsAdapter(getDummyNewsList())
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNews.adapter = adapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getDummyNewsList(): ArrayList<Dummy> {
        val dummyList = ArrayList<Dummy>()
        dummyList.add(Dummy("News 1", "June 1, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("News 2", "June 2, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("News 3", "June 3, 2023", R.drawable.home_banner))
        return dummyList
    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }
}