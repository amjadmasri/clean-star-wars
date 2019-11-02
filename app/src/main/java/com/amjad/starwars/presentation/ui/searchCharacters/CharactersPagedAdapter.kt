package com.amjad.starwars.presentation.ui.searchCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amjad.starwars.R
import com.amjad.starwars.data.models.CharacterDataModel


class CharactersPagedAdapter(characterDiffCallBacks: CharacterDiffCallBacks) :
    PagedListAdapter<CharacterDataModel, CharactersPagedAdapter.CharacterSearchViewHolder>(
        characterDiffCallBacks
    ) {

    override fun onBindViewHolder(holder: CharacterSearchViewHolder, position: Int) {
        val mvImagesLocal: CharacterDataModel? = getItem(position)

        holder.bindTo(mvImagesLocal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSearchViewHolder =
        CharacterSearchViewHolder(parent)


    class CharacterSearchViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.character_search_item, parent, false)
    ) {

        private val nameText = itemView.findViewById<TextView>(R.id.character_name)
        private val birthdateText = itemView.findViewById<TextView>(R.id.character_birthdate)
        private var characterDataModel: CharacterDataModel? = null


        fun bindTo(characterDataModel: CharacterDataModel?) {
            this.characterDataModel = characterDataModel


            nameText.text = characterDataModel?.name
            birthdateText.text = characterDataModel?.birthYear
        }

    }
}