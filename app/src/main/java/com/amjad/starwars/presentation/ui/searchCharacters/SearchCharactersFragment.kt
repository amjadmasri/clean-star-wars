package com.amjad.starwars.presentation.ui.searchCharacters


import android.app.SearchManager
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.amjad.starwars.R
import com.amjad.starwars.common.extensions.plusAssign
import com.amjad.starwars.common.models.Status
import com.amjad.starwars.domain.models.CharacterDomainModel
import com.amjad.starwars.presentation.ui.base.BaseFragment
import com.amjad.starwars.presentation.viewModels.SearchCharacterViewModel
import com.amjad.starwars.presentation.viewModels.ViewModelProviderFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_search_charracters.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

/**
 * A simple [Fragment] subclass.
 */
class SearchCharactersFragment : BaseFragment(),
    CharactersPagedAdapter.CharacterAdapterListener {

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

    private val disposable:CompositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()

        navController = Navigation.findNavController(view)

        viewModel = ViewModelProvider(this, viewModelProviderFactory)
            .get(SearchCharacterViewModel::class.java)

        viewModel.observeSearchResults()
            .observe(viewLifecycleOwner, Observer {
                renderData(it)
            })

        viewModel.observeErrorMessage()
            .observe(viewLifecycleOwner, Observer {
                showError(it)
            })

    }



    private fun setupUi() {
        val manager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        val si = manager.getSearchableInfo(activity?.componentName)
        val options = searchView.imeOptions
        searchView.imeOptions = options or EditorInfo.IME_FLAG_NO_EXTRACT_UI
        searchView.setSearchableInfo(si)

        disposable+=createObservableFromView(searchView)
            .debounce(200, TimeUnit.MILLISECONDS)
            .filter { text -> text.isNotEmpty() }
            .distinctUntilChanged()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                submitAndObserveSearch(it)
            }

        character_recycler.layoutManager = linearLayoutManager.get()
        character_recycler.adapter = charactersPagedAdapter

        charactersPagedAdapter.listener = this

    }

    override fun onCharacterClick(id: String) {
        navController.navigate(
            SearchCharactersFragmentDirections.actionSearchCharractersFragmentToCharacterDetailsFragment(
                id
            )
        )
    }

    private fun createObservableFromView(searchView: SearchView): Observable<String> {
        val subject = PublishSubject.create<String>()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onComplete()
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return false
            }
        })

        return subject
    }


    private fun submitAndObserveSearch(name: String) {
        viewModel.setSearchString(name)
    }

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

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }
}
