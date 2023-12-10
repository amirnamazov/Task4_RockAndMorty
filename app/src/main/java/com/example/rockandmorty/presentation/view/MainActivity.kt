package com.example.rockandmorty.presentation.view

import com.example.rockandmorty.databinding.ActivityMainBinding
import com.example.rockandmorty.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun initialize() {}
}