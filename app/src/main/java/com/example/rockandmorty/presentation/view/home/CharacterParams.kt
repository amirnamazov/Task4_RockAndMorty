package com.example.rockandmorty.presentation.view.home

data class CharacterParams(
    val spinnerList: List<String>,
) {
    var selectedPos: Int = 0
    val params: List<String> = spinnerList.mapIndexed { index, s -> if (index == 0) "" else s }
}