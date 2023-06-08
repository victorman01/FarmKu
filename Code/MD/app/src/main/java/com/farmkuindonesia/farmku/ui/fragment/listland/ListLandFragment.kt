package com.farmkuindonesia.farmku.ui.fragment.listland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.FragmentListLandBinding
import com.farmkuindonesia.farmku.ui.fragment.home.Dummy


class ListLandFragment : Fragment() {
    private var _binding: FragmentListLandBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ListLandAdapter(getDummyNewsList())
        binding.rvLands.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLands.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListLandBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getDummyNewsList(): ArrayList<Dummy> {
        val dummyList = ArrayList<Dummy>()
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 1, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 2, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 3, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 4, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 5, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 6, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 7, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 8, 2023", R.drawable.home_banner))
        dummyList.add(Dummy("Sawah Padi Rojo Lele", "June 9, 2023", R.drawable.home_banner))
        return dummyList
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListLandFragment().apply {}
    }
}