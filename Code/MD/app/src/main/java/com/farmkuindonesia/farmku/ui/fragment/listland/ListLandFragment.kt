package com.farmkuindonesia.farmku.ui.fragment.listland

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.database.responses.LandItem
import com.farmkuindonesia.farmku.databinding.FragmentListLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.fragment.listland.addland.AddLandActivity


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
        val id = listLandViewModel.getUserId().id.toString()
        var listLand: List<LandItem?>?
        listLandViewModel.getListLand(id)
        listLand = listOf()
        listLandViewModel.listLand.observe(this.viewLifecycleOwner) {
            listLand = it
            if (listLand.isNullOrEmpty()) {
                binding.apply {
                    txtEmptyListLand.text = getString(R.string.ups_anda_belum_mendaftarkan_lahan_anda_text)
                    txtEmptyListLand.visibility = View.VISIBLE
                    imgNullData.visibility = View.VISIBLE
                }
            } else {
                val adapter = listLand?.let { land -> ListLandAdapter(land) }
                binding.rvLands.layoutManager = LinearLayoutManager(requireContext())
                binding.rvLands.adapter = adapter
            }
        }
        binding.fabAddLand.setOnClickListener {
            val intent = Intent(requireContext(), AddLandActivity::class.java)
            startActivity(intent)
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