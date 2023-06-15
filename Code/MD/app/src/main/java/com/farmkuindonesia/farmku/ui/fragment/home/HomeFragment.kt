package com.farmkuindonesia.farmku.ui.fragment.home

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.database.model.User
import com.farmkuindonesia.farmku.databinding.FragmentHomeBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.forgotpassword.ForgotPasswordActivity
import com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit.DiseaseDetectionActivity
import com.farmkuindonesia.farmku.ui.fragment.home.hotnews.ListHotNewsAdapter
import com.farmkuindonesia.farmku.ui.fragment.home.hotnews.HotNews
import com.farmkuindonesia.farmku.ui.soildatacollection.SoilDataCollectionActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private lateinit var viewModelFac: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFac = ViewModelFactory.getInstance(this.requireContext())
        homeFragmentViewModel = ViewModelProvider(this, viewModelFac)[HomeFragmentViewModel::class.java]

        val user = homeFragmentViewModel.getUser()
        val adapter = ListHotNewsAdapter(HotNews().getDummyNewsList())

        binding.apply {
            txtHiName.text = "Hi, ${user.name}"

            rvNews.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            rvNews.adapter = adapter

            btnDeteksiPenyakit.setOnClickListener {
                val intent = Intent(requireContext(), DiseaseDetectionActivity::class.java)
                startActivity(intent)
            }
            btnData.setOnClickListener{
                val intent = Intent(requireContext(), SoilDataCollectionActivity::class.java)
                startActivity(intent)
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
