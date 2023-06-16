package com.farmkuindonesia.farmku.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.database.responses.LandItem
import com.farmkuindonesia.farmku.databinding.FragmentHomeBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit.DiseaseDetectionActivity
import com.farmkuindonesia.farmku.ui.fragment.home.hotnews.ListHotNewsAdapter
import com.farmkuindonesia.farmku.ui.fragment.home.hotnews.HotNews
import com.farmkuindonesia.farmku.ui.soildatacollection.SoilDataCollectionActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory.getInstance(requireContext())
        homeFragmentViewModel = ViewModelProvider(this, viewModelFactory)[HomeFragmentViewModel::class.java]

        val adapter = ListHotNewsAdapter(HotNews().getDummyNewsList())
        val user = homeFragmentViewModel.getUser()

        homeFragmentViewModel.getCountLand(user.id.toString())
        homeFragmentViewModel.landCount.observe(viewLifecycleOwner) { landCount ->
            val listLand: List<LandItem?> = landCount ?: emptyList()

            binding.apply {
                txtHiName.text = "Hi, ${user.name}"
                txtJumlahLahan.text = listLand.size.toString()
                rvNews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                rvNews.adapter = adapter

                btnDeteksiPenyakit.setOnClickListener {
                    val intent = Intent(requireContext(), DiseaseDetectionActivity::class.java)
                    startActivity(intent)
                }

                btnData.setOnClickListener {
                    val intent = Intent(requireContext(), SoilDataCollectionActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
