package com.neonusa.belajarkanjijlpt.di

import DetailViewModel
import com.neonusa.belajarkanjijlpt.ui.kanjioftheday.KanjiOfTheDayViewModel
import com.neonusa.belajarkanjijlpt.ui.learned.LearnedViewModel
import com.neonusa.belajarkanjijlpt.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailViewModel() }
    viewModel { MainViewModel() }
    viewModel { LearnedViewModel() }
    viewModel { KanjiOfTheDayViewModel() }
}