package com.example.projectimam.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.projectimam.*
import com.example.projectimam.databinding.FragmentMeBinding
import com.google.firebase.auth.FirebaseAuth

class MeFragment : Fragment() {

    private var _binding: FragmentMeBinding? = null
    private lateinit var mAuth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_placeholder_profile_picture)
            .circleCrop()
            .into(binding.profilePicture)

        binding.username.text = currentUser?.displayName

        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            val intent = Intent (activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnMyGoals.setOnClickListener{
            val intent = Intent(activity, MyGoalsActivity::class.java)
            startActivity(intent)
        }

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}