package com.farmkuindonesia.farmku.ui.fragment.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.FragmentProfileBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.fragment.home.HomeFragmentViewModel
import com.farmkuindonesia.farmku.ui.main.MainActivity

class ProfileFragment : Fragment(), ListProfileItemAdapter.MainActivityCallback {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var viewModelFac: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFac = ViewModelFactory.getInstance(this.requireContext())
        profileViewModel = ViewModelProvider(this, viewModelFac)[ProfileViewModel::class.java]

        val user = profileViewModel.getUser()

        binding.apply {
            txtNameProfile.text = user.name
            txtNumberProfile.text = user.phoneNumber
        }

        val mainActivity = activity as MainActivity
        val adapter = ListProfileItemAdapter(requireContext(),getProfileItemList(), mainActivity)
        binding.apply {
            rvProfileItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvProfileItem.isNestedScrollingEnabled = false
            rvProfileItem.setHasFixedSize(true)
            rvProfileItem.adapter = adapter
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getProfileItemList(): ArrayList<ProfileItemData> {
        val profileItemList = ArrayList<ProfileItemData>()
        profileItemList.add(
            ProfileItemData(
                "Ubah Data Profil Saya",
                R.drawable.person_setting_icon
            )
        )
        profileItemList.add(
            ProfileItemData(
                "Pengaturan",
                R.drawable.settings_icon
            )
        )
        profileItemList.add(
            ProfileItemData(
                "Bantuan",
                R.drawable.help_icon
            )
        )
        profileItemList.add(
            ProfileItemData(
                "Syarat dan Ketentuan",
                R.drawable.article_icon
            )
        )
        profileItemList.add(
            ProfileItemData(
                "Tentang Aplikasi",
                R.drawable.info_icon
            )
        )
        profileItemList.add(
            ProfileItemData(
                "Keluar",
                R.drawable.logout_icon
            )
        )
        return profileItemList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun finishMainActivity() {
        activity?.finish()
    }
}