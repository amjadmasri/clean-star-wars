package com.amjad.starwars.presentation.ui.characterDetails


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.R
import com.amjad.starwars.domain.models.FilmDomainModel
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.models.SpeciesPresentationModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
import com.amjad.starwars.presentation.viewModels.CharacterDetailsViewModel
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_character_details.*
import javax.inject.Inject
import javax.inject.Provider


class CharacterDetailsFragment : BaseFragment() {
    override fun getLayoutRes(): Int = R.layout.fragment_character_details

    override fun attachFragmentInteractionListener(context: Context) {}

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var linearLayoutManager: Provider<LinearLayoutManager>

    @Inject
    lateinit var filmAdapter: FilmAdapter

    private lateinit var viewModel: CharacterDetailsViewModel

    private lateinit var navController: NavController

    private lateinit var characterId: String
    private val disposable :CompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(CharacterDetailsViewModel::class.java)

        characterId = CharacterDetailsFragmentArgs.fromBundle(requireArguments()).characterId

        viewModel.getCharacterDetails(characterId)

        viewModel.observeCharacterResult()
            .observe(viewLifecycleOwner, Observer {
                renderCharacterDetails(it)
            })

        viewModel.observeSpeciesDetails()
            .observe(viewLifecycleOwner, Observer {
                renderSpeciesDetails(it)
            })

        viewModel.observeFilmList()
            .observe(viewLifecycleOwner, Observer {
                renderFilmList(it)
            })

        viewModel.observeErrorMessage()
            .observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })

    }

    private fun renderFilmList(filmList: List<FilmDomainModel>) {
        film_loading.hide()
        filmAdapter.setData(filmList)
    }

    private fun renderSpeciesDetails(species: SpeciesPresentationModel) {
        species_loading.hide()
        species_name_text.text = species.name
        language_text.text = species.language

        homeworld_loading.hide()
        planet_name_text.text = species.homeWorld?.name
        population_text.text = species.homeWorld?.population
    }

    private fun renderCharacterDetails(data: CharacterPresentationModel) {
        character_loading.hide()
        name_text.text = data.name
        birthdate_text.text = data.birthYear
        height_cm.text = data.heightInCm
        height_inches.text = data.heightInches
        hieght_feet.text = data.heightFeet
    }

    private fun setupUi() {
        film_recycler.layoutManager = linearLayoutManager.get()
        film_recycler.adapter = filmAdapter

    }
}
