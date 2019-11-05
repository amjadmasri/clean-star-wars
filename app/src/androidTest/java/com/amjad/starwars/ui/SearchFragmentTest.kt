package com.amjad.starwars.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amjad.starwars.R
import com.amjad.starwars.presentation.ui.searchCharacters.SearchCharactersFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchFragmentTest {



    @Test
    fun ensureTextChangesWork() {

        val mockNavController = mock(NavController::class.java)
        val titleScenario = launchFragmentInContainer<SearchCharactersFragment>()

        titleScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.searchView))
            .perform(SearchViewActionExtension.submitText("search text"))



        onView(withId(R.id.loading)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }


}