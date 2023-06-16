package com.farmkuindonesia.farmku.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.databinding.FragmentNewsBinding
import com.farmkuindonesia.farmku.database.model.Dummy

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
        dummyList.add(
            Dummy(
                "Perbedaan Panen Padi Manual dan Mekanis",
                "Dipublikasikan: June 1, 2023",
                "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA1axARX.img?w=750&h=500&m=6&x=120&y=120&s=280&d=280"
            )
        )
        dummyList.add(
            Dummy(
                "Daerah yang Jadi Lumbung Padi di Jawa Tengah",
                "Dipublikasikan: June 2, 2023",
                "https://radarmadiun.jawapos.com/wp-content/uploads/2023/06/1_DSCN7569.jpg"
            )
        )
        dummyList.add(
            Dummy(
                "Tahun Ke-5 Perbaikan Genetik Padi Merah Munduk, Dirancang Panen Cepat dan Cocok di Semua Lahan",
                "Dipublikasikan: June 2, 2023",
                "https://www.nusabali.com/article_images/143846/tahun-ke-5-perbaikan-genetik-padi-merah-munduk-di-800-2023-06-14-051557_0.jpg"
            )
        )
        dummyList.add(
            Dummy(
                "Dilanda Musim Kemarau, Tanaman Padi Warga Nagan Raya Terancam Mati",
                "Dipublikasikan: June 2, 2023",
                "https://cdn.habaaceh.id/files/images/20230614-whatsapp-image-2023-06-14-at-11-57-13-1.jpeg"
            )
        )
        return dummyList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}