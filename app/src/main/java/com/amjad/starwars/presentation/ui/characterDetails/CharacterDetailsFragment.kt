package com.amjad.starwars.presentation.ui.characterDetails


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.R
import com.amjad.starwars.common.Status
import com.amjad.starwars.presentation.models.CharacterPresentationModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
import com.amjad.starwars.presentation.viewModels.CharacterDetailsViewModel
import com.amjad.starwars.presentation.viewModels.SearchCharacterViewModel
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory
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

    private lateinit var characterId:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(CharacterDetailsViewModel::class.java)

        characterId = CharacterDetailsFragmentArgs.fromBundle(arguments!!).characterId

        viewModel.getCharacterDetails(characterId)
            .observe(this, Observer {
                if(it.status==Status.SUCCESS){
                    render(it.data!!)
                }
            })
    }

    private fun render(data: CharacterPresentationModel) {
        name_text.text=data.name
        birthdate_text.text=data.birthYear
        height_cm.text=data.heightInCm
        height_inches.text=data.heightInches
        hieght_feet.text=data.heightFeet

        planet_name_text.text=data.homeworld?.name
        population_text.text=data.homeworld?.population

        species_name_text.text=data.species?.name
        language_text.text=data.species?.language

        filmAdapter.setData(data.films)
    }

    private fun setupUi() {
        film_recycler.layoutManager=linearLayoutManager.get()
        film_recycler.adapter=filmAdapter

    }
}
