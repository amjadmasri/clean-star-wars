package com.amjad.starwars.domain.models

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.amjad.starwars.common.models.Resource

data class PagedListing<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val networkState: LiveData<Resource<String>>
)
