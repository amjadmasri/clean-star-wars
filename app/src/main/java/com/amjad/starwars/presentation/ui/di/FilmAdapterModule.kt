package com.amjad.starwars.presentation.ui.di

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.presentation.ui.characterDetails.FilmAdapter
import dagger.Module
import dagger.Provides

@Module
class FilmAdapterModule {

    @Provides
    fun provideFilmAdapter(): FilmAdapter {
        return FilmAdapter(mutableListOf())
    }

    @Provides
    internal fun provideLinearLayoutManager(
        context: Context
    ): LinearLayoutManager {
        val linearLayoutManager =LinearLayoutManager(context)
        linearLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
        return linearLayoutManager
    }

}