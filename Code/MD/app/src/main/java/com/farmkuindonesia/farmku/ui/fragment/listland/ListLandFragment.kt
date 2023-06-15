package com.farmkuindonesia.farmku.ui.fragment.listland

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.database.responses.LandItem
import com.farmkuindonesia.farmku.databinding.FragmentListLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory


class ListLandFragment : Fragment() {
    private var _binding: FragmentListLandBinding? = null
    private val binding get() = _binding!!
    private lateinit var listLandViewModel: ListLandViewModel
    private lateinit var viewModelFac: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelFac = ViewModelFactory.getInstance(this.requireContext())
        listLandViewModel = ViewModelProvider(this, viewModelFac)[ListLandViewModel::class.java]
//        val id = listLandViewModel.getUserId().id.toString()
        val id = "2cb9c27e-cc71-4870-95c2-a2a2f4aad07a"
        var listLand: List<LandItem?>?
        listLandViewModel.getListLand(id)
        listLandViewModel.listLand.observe(this.viewLifecycleOwner) {
            listLand = it
            if(listLand == null){
                binding.apply {
                    txtEmptyListLand.text = "Ups, Anda belum mendaftarkan lahan anda"
                    txtEmptyListLand.visibility = View.VISIBLE
                    imgNullData.visibility = View.VISIBLE
                }
            }else{
                val adapter = listLand?.let {land -> ListLandAdapter(land) }
                binding.rvLands.layoutManager = LinearLayoutManager(requireContext())
                binding.rvLands.adapter = adapter
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListLandBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListLandFragment().apply {}
    }
}