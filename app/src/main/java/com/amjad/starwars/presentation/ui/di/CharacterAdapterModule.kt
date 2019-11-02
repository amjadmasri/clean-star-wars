package com.amjad.starwars.presentation.ui.di

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.presentation.ui.searchCharacters.CharacterDiffCallBacks
import com.amjad.starwars.presentation.ui.searchCharacters.CharactersPagedAdapter
import dagger.Module
import dagger.Provides

@Module
class CharacterAdapterModule {

    @Provides
    fun provideMVImagePagedAdapter(characterDiffCallBacks: CharacterDiffCallBacks): CharactersPagedAdapter {
        return CharactersPagedAdapter(characterDiffCallBacks)
    }

    @Provides
    internal fun provideLinearLayoutManager(context: Context
    ): LinearLayoutManager =LinearLayoutManager(context)


    @Provides
    fun provideCharacterDiffCallBacks(): CharacterDiffCallBacks {
        return CharacterDiffCallBacks()
    }
}