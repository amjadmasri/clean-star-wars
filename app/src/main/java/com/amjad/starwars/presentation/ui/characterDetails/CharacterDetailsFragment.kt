package com.amjad.starwars.presentation.ui.characterDetails


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.R
import com.amjad.starwars.common.models.Status
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
import com.amjad.starwars.presentation.viewModels.CharacterDetailsViewModel
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

        characterId = CharacterDetailsFragmentArgs.fromBundle(arguments!!).characterId

        viewModel.getCharacterDetails(characterId)

        viewModel.observeCharacterResult()
            .observe(viewLifecycleOwner, Observer {
                render(it)
            })

    }

    private fun render(data: CharacterPresentationModel) {
        character_loading.hide()
        name_text.text = data.name
        birthdate_text.text = data.birthYear
        height_cm.text = data.heightInCm
        height_inches.text = data.heightInches
        hieght_feet.text = data.heightFeet

        val species=data.species
        if (species != null) {
            species_loading.hide()
            species_name_text.text = species.name
            language_text.text = species.language
        }

        if (species?.homeworld != null) {
            homeworld_loading.hide()
            planet_name_text.text = species.homeworld?.name
            population_text.text = species.homeworld?.population
        }

        if(data.films.size>0) {
            film_loading.hide()
            filmAdapter.setData(data.films)
        }
    }

    private fun setupUi() {
        film_recycler.layoutManager = linearLayoutManager.get()
        film_recycler.adapter = filmAdapter

    }
}
