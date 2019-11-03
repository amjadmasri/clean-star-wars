package com.amjad.starwars.presentation.ui.characterDetails

import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amjad.starwars.R
import com.amjad.starwars.domain.models.FilmDomainModel

class FilmAdapter constructor(private val filmList: MutableList<FilmDomainModel>) :
    RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder =
        FilmViewHolder(parent)


    override fun getItemCount(): Int =
        filmList.size


    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bindViewHolder(filmList[position])
    }

    fun setData(newData: List<FilmDomainModel>) {
        filmList.clear()
        filmList.addAll(newData)
        notifyDataSetChanged()
    }


    class FilmViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.film_item, parent, false
        )
    ) {

        private val filmTitle = itemView.findViewById<TextView>(R.id.film_title)
        private val filmReleaseDate = itemView.findViewById<TextView>(R.id.film_release_date)
        private val filmOpeningCrawl = itemView.findViewById<TextView>(R.id.openeing_crawl_text)
        fun bindViewHolder(filmDomainModel: FilmDomainModel) {
            filmTitle.text = filmDomainModel.title
            filmReleaseDate.text = filmDomainModel.releaseDate
            filmOpeningCrawl.text = filmDomainModel.openingCrawl

        }

    }
}