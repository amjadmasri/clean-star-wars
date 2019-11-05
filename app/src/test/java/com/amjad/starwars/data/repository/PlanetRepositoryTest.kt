package com.amjad.starwars.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amjad.starwars.ObjectsProvider
import com.amjad.starwars.common.models.Resource
import com.amjad.starwars.data.mappers.PlanetMapper
import com.amjad.starwars.data.models.PlanetDataModel
import com.amjad.starwars.data.remote.PlanetRemoteSourceImp
import com.amjad.starwars.domain.models.PlanetDomainModel
import com.amjad.starwars.observeForTesting
import io.reactivex.Single
import junit.framework.Assert.assertEquals
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
    lateinit var response :Response<PlanetDataModel>

    lateinit var planetRepositoryImp: PlanetRepositoryImp

    @Before
    fun setup(){
        planetRepositoryImp = PlanetRepositoryImp(planetRemoteSourceImp,planetMapper)

    }

    @Test
    fun testResultIsResourceLoading(){
        val obj =ObjectsProvider.providePlanetRemoteModel()
        Mockito.`when`(planetRemoteSourceImp.getPlanetDetails("1")).thenReturn(Single.just(response))
        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(obj)
        val liveDataViewModel= planetRepositoryImp.getPlanetDetails("1")
        val resource: Resource<PlanetDomainModel> = Resource.loading()
        liveDataViewModel .observeForTesting{
            assertEquals(liveDataViewModel.value,resource )
        }
    }
}