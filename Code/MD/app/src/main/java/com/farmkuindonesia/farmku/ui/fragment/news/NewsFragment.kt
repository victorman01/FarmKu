package com.farmkuindonesia.farmku.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.FragmentNewsBinding
import com.farmkuindonesia.farmku.ui.fragment.home.hotnews.Dummy

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListNewsAdapter(getDummyNewsList())
        binding.rvNewsNews.adapter = adapter
        binding.rvNewsNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    fun getDummyNewsList(): ArrayList<Dummy> {
        val dummyList = ArrayList<Dummy>()
        dummyList.add(
            Dummy(
                "Merangkak Naik, Harga TBS Sawit di Babel Naik Rp 100 dari Rp 1.300 per Kg menjadi Rp 1.400 per Kg",
                "Dipublikasikan: June 1, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Distan Mataram mengoptimalkan pengolahan tanah tingkatkan produksi padi",
                "Dipublikasikan: June 2, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Gagal Tanam Padi, Petani Desa Rahayu Minta PHE TEJ Beri Ganti Rugi",
                "Dipublikasikan: June 3, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Merangkak Naik, Harga TBS Sawit di Babel Naik Rp 100 dari Rp 1.300 per Kg menjadi Rp 1.400 per Kg",
                "Dipublikasikan: June 1, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Distan Mataram mengoptimalkan pengolahan tanah tingkatkan produksi padi",
                "Dipublikasikan: June 2, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Gagal Tanam Padi, Petani Desa Rahayu Minta PHE TEJ Beri Ganti Rugi",
                "Dipublikasikan: June 3, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Merangkak Naik, Harga TBS Sawit di Babel Naik Rp 100 dari Rp 1.300 per Kg menjadi Rp 1.400 per Kg",
                "Dipublikasikan: June 1, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Distan Mataram mengoptimalkan pengolahan tanah tingkatkan produksi padi",
                "Dipublikasikan: June 2, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Gagal Tanam Padi, Petani Desa Rahayu Minta PHE TEJ Beri Ganti Rugi",
                "Dipublikasikan: June 3, 2023",
                R.drawable.home_banner
            )
        )
        return dummyList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}