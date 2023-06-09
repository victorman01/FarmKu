package com.farmkuindonesia.farmku.ui.fragment.home.hotnews

import com.farmkuindonesia.farmku.R

class HotNews {
    fun getDummyNewsList(): ArrayList<Dummy> {
        val dummyList = ArrayList<Dummy>()
        dummyList.add(
            Dummy(
                "Merangkak Naik, Harga TBS Sawit di Babel Naik Rp 100 dari Rp 1.300 per Kg menjadi Rp 1.400 per Kg",
                "June 1, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Distan Mataram mengoptimalkan pengolahan tanah tingkatkan produksi padi",
                "June 2, 2023",
                R.drawable.home_banner
            )
        )
        dummyList.add(
            Dummy(
                "Gagal Tanam Padi, Petani Desa Rahayu Minta PHE TEJ Beri Ganti Rugi",
                "June 3, 2023",
                R.drawable.home_banner
            )
        )
        return dummyList
    }
}