package com.amjad.starwars.presentation.ui.searchCharacters


import android.app.SearchManager
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager

import com.amjad.starwars.R
import com.amjad.starwars.data.models.CharacterDataModel
import com.amjad.starwars.common.Status
import com.amjad.starwars.presentation.viewModels.SearchCharacterViewModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
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
        Log.d("amjad","loading")
        loading_bar.visibility=View.VISIBLE
    }

    private fun renderData(data: PagedList<CharacterDataModel>?) {
        loading_bar.visibility=View.GONE
        charactersPagedAdapter.submitList(data)

        if(!data!!.isEmpty()){
            Toast.makeText(activity,"No Results found ",Toast.LENGTH_LONG).show()
        }
    }

    override fun getLayoutRes(): Int =R.layout.fragment_search_charracters

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
    }

    private fun setupUi() {
        val manager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        val si = manager.getSearchableInfo(activity?.componentName)
        val options = searchView.imeOptions
        searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView.setSearchableInfo(si)

        searchView.setOnQueryTextListener(this)

        character_recycler.layoutManager=linearLayoutManager.get()
        character_recycler.adapter=charactersPagedAdapter

        charactersPagedAdapter.listener=this

    }

    override fun onCharacterClick(id:String?) {
        navController.navigate(SearchCharactersFragmentDirections.actionSearchCharractersFragmentToCharacterDetailsFragment(id!!))
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) {
            viewModel.searchCharacterByName(query)
                .observe(this, Observer {
                    when(it.status){
                        Status.SUCCESS->  renderData(it.data)
                        Status.LOADING -> showLoading()
                    }

                })
        }

        return true
    }

}
