package com.amjad.starwars.presentation.ui.searchCharacters

import androidx.recyclerview.widget.DiffUtil
import com.amjad.starwars.data.models.CharacterDataModel

class CharacterDiffCallBacks : DiffUtil.ItemCallback<CharacterDataModel>() {
    override fun areItemsTheSame(
        oldItem: CharacterDataModel,
        newItem: CharacterDataModel
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun getChangePayload(oldItem: CharacterDataModel, newItem: CharacterDataModel): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

    override fun areContentsTheSame(
        oldItem: CharacterDataModel,
        newItem: CharacterDataModel
    ): Boolean {
        return oldItem == newItem
    }
}