package com.example.mygithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mygithubuser.databinding.FragmentFollowBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class FollowFragment : Fragment() {

    private var fragmentFollowBinding: FragmentFollowBinding? = null
    private val binding get() = fragmentFollowBinding!!

    private lateinit var followViewModel: FollowViewModel

    private var position: Int = 0
    private var username: String? = ""

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentFollowBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]

        val layoutManager = LinearLayoutManager(requireActivity())
        fragmentFollowBinding?.rvFollow?.layoutManager = layoutManager

        if (position == 1) {
            followViewModel.findFollower(username.toString())
            followViewModel.listFollower.observe(viewLifecycleOwner) {listFollower ->
                setFollower(listFollower)
            }
        }
        else {
            followViewModel.findFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) {listFollowing ->
                setFollowing(listFollowing)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

    }

    private fun setFollower(listFollower: List<ItemsItem>) {
        val adapter = FollowAdapter(listFollower)
        fragmentFollowBinding?.rvFollow?.adapter = adapter
    }

    private fun setFollowing(listFollowing: List<ItemsItem>) {
        val adapter = FollowAdapter(listFollowing)
        fragmentFollowBinding?.rvFollow?.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        fragmentFollowBinding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFollowBinding = null
    }
}