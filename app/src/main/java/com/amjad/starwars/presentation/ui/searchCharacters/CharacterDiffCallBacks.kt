package com.amjad.starwars.presentation.ui.searchCharacters

import androidx.recyclerview.widget.DiffUtil
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.domain.models.CharacterDomainModel

class CharacterDiffCallBacks : DiffUtil.ItemCallback<CharacterDomainModel>() {
    override fun areItemsTheSame(
        oldItem: CharacterDomainModel,
        newItem: CharacterDomainModel
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: CharacterDomainModel,
        newItem: CharacterDomainModel
    ): Boolean {
        return oldItem == newItem
    }
}