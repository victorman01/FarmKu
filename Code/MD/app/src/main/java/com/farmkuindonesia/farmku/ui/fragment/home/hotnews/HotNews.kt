package com.farmkuindonesia.farmku.ui.fragment.home.hotnews

import com.farmkuindonesia.farmku.database.model.Dummy

class HotNews {
    fun getDummyNewsList(): ArrayList<Dummy> {
        val dummyList = ArrayList<Dummy>()
        dummyList.add(
            Dummy(
                "Ribuan Hektare Sawah di Kabupaten Bandung Barat Terancam Kekeringan, Pemkab Siapkan Cadangan Pangan",
                "Dipublikasikan: June 1, 2023",
                "https://cdn-2.tstatic.net/jabar/foto/bank/images/lahan-tembakau-rusak-karena-kering.jpg"
            )
        )
        dummyList.add(
            Dummy(
                "Upaya Petani Padi Sleman Hadapi Potensi Kekeringan",
                "Dipublikasikan: June 2, 2023",
                "https://static.republika.co.id/uploads/images/inpicture_slide/089398700-1642066877-830-556.jpg"
            )
        )
        dummyList.add(
            Dummy(
                "Mengenal Mangenta, Tradisi Menyambut Masa Panen Padi Suku Dayak",
                "Dipublikasikan: June 3, 2023",
                "https://cdn.medcom.id/dynamic/content/2023/06/14/1579013/WKRfmQrJtq.jpg?w=1024"
            )
        )
        return dummyList
    }
}