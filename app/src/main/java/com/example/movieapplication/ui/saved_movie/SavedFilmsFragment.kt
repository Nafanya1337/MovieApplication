package com.example.movieapplication.ui.saved_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.movieapplication.databinding.FragmentSavedFilmsBinding
import com.example.movieapplication.ui.MainActivityViewModel
import com.example.movieapplication.ui.popular_movie.PopularFilmsFragment


class SavedFilmsFragment : Fragment() {

    private lateinit var binding: FragmentSavedFilmsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}