package com.amjad.starwars.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.data.remote.PlanetRemoteSourceImp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PlanetRepositoryTest {

    private fun <T> anyObject(): T {
        return Mockito.any<T>()
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var planetRemoteSourceImp: PlanetRemoteSourceImp

    @Mock
    lateinit var planetMapper: PlanetMapper

    @Mock
    lateinit var response: Response<PlanetDataModel>

    lateinit var planetRepositoryImp: PlanetRepositoryImp

    @Before
    fun setup() {
        planetRepositoryImp = PlanetRepositoryImp(planetRemoteSourceImp, planetMapper)

    }

    @Test
    fun testResultIsResourceLoading() {

    }
}