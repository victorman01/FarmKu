package com.farmkuindonesia.farmku.ui.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        dummyList.add(Dummy("Merangkak Naik, Harga TBS Sawit di Babel Naik Rp 100 dari Rp 1.300 per Kg menjadi Rp 1.400 per Kg", "June 1, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Distan Mataram mengoptimalkan pengolahan tanah tingkatkan produksi padi", "June 2, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Gagal Tanam Padi, Petani Desa Rahayu Minta PHE TEJ Beri Ganti Rugi", "June 3, 2023", R.drawable.home_banner))
        return dummyList
    }
    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }
}