package com.amjad.starwars.presentation.ui.searchCharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amjad.starwars.R
import com.amjad.starwars.data.models.CharacterDataModel


class CharactersPagedAdapter(characterDiffCallBacks: CharacterDiffCallBacks) :
    PagedListAdapter<CharacterDataModel, CharactersPagedAdapter.CharacterSearchViewHolder>(
        characterDiffCallBacks
    ) {

    lateinit var listener:CharacterAdapterListener

    override fun onBindViewHolder(holder: CharacterSearchViewHolder, position: Int) {
        val characterDataModel: CharacterDataModel? = getItem(position)

        holder.bindTo(characterDataModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterSearchViewHolder =
        CharacterSearchViewHolder(parent,listener)

    interface CharacterAdapterListener {

        fun onCharacterClick(id:String?)
    }

    class CharacterSearchViewHolder(parent: ViewGroup, val listenrer:CharacterAdapterListener) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.character_search_item, parent, false)
    ) {

        private val characterCard =itemView.findViewById<CardView>(R.id.character_card)
        private val nameText = itemView.findViewById<TextView>(R.id.character_name)
        private val birthdateText = itemView.findViewById<TextView>(R.id.character_birthdate)
        private var characterDataModel: CharacterDataModel? = null


        fun bindTo(characterDataModel: CharacterDataModel?) {
            this.characterDataModel = characterDataModel


            nameText.text = characterDataModel?.name
            birthdateText.text = characterDataModel?.birthYear

            characterCard.setOnClickListener { listenrer.onCharacterClick(characterDataModel?.url) }
        }

    }
}