package com.amjad.starwars.presentation.ui.searchCharacters


import android.app.SearchManager
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.R
import com.amjad.starwars.common.models.Status
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
import com.amjad.starwars.presentation.viewModels.SearchCharacterViewModel
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_search_charracters.*
import javax.inject.Inject
import javax.inject.Provider

/**
 * A simple [Fragment] subclass.
 */
class SearchCharactersFragment : BaseFragment(), SearchView.OnQueryTextListener,
    CharactersPagedAdapter.CharacterAdapterListener {


    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun removeLoading(){
        loading.visibility=View.GONE
    }

    private fun showError(message:String?){
        removeLoading()
        Toast.makeText(activity, message?:"there was an error", Toast.LENGTH_LONG).show()
    }

    private fun renderData(data: PagedList<CharacterDomainModel>?) {

        charactersPagedAdapter.submitList(data)

    }

    override fun getLayoutRes(): Int = R.layout.fragment_search_charracters

    override fun attachFragmentInteractionListener(context: Context) {
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    @Inject
    lateinit var charactersPagedAdapter: CharactersPagedAdapter

    private lateinit var viewModel: SearchCharacterViewModel

    @Inject
    lateinit var linearLayoutManager: Provider<LinearLayoutManager>

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(SearchCharacterViewModel::class.java)

        viewModel.observeSearchResults()
            .observe(this, Observer {
                renderData(it)
            })


        viewModel.observeNetworkState().observe(this, Observer {
            when(it.status){
                Status.SUCCESS-> removeLoading()
                Status.LOADING->showLoading()
                Status.ERROR->showError(it.message)
            }
        })

    }



    private fun setupUi() {
        val manager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        val si = manager.getSearchableInfo(activity?.componentName)
        val options = searchView.imeOptions
        searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView.setSearchableInfo(si)

        searchView.setOnQueryTextListener(this)

        character_recycler.layoutManager = linearLayoutManager.get()
        character_recycler.adapter = charactersPagedAdapter

        charactersPagedAdapter.listener = this

    }

    override fun onCharacterClick(id: String?) {
        navController.navigate(
            SearchCharactersFragmentDirections.actionSearchCharractersFragmentToCharacterDetailsFragment(
                id!!
            )
        )
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText.equals("")){
            this.onQueryTextSubmit("")
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        var name = query ?: ""


        submitAndObserveSearch(name)


        return true
    }

    private fun submitAndObserveSearch(name: String) {
        viewModel.setStringListener(name)


    }

}
