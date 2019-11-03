package com.amjad.starwars.data.mappers

import com.amjad.starwars.ObjectsProvider
import com.amjad.starwars.common.utilities.UrlExtractor
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FilmMapperTest {

    @Mock
    lateinit var urlExtractor: UrlExtractor

    private lateinit var filmMapper: FilmMapper

    @Before
    fun setup(){
        filmMapper =FilmMapper(urlExtractor)
        Mockito.`when`(urlExtractor.extractList(listOf("1"))).thenReturn(listOf("1"))

        Mockito.`when`(urlExtractor.extract("http://www.example.com/1")).thenReturn("1")
    }

    @Test
    fun checkRemoteToDomainValues(){
        val obj = ObjectsProvider.provideFilmRemoteModel()

        val result = filmMapper.mapFromEntity(obj)

        assertEquals(obj.created,result.created)
        assertEquals(obj.characters.size,result.characters.size)
        assertEquals("1",result.characters[0])
        assertEquals(obj.director,result.director)
        assertEquals(obj.edited,result.edited)
        assertEquals(obj.episodeId,result.episodeId)
        assertEquals(obj.openingCrawl,result.openingCrawl)
        assertEquals(obj.producer,result.producer)
        assertEquals(obj.releaseDate,result.releaseDate)
        assertEquals(obj.title,result.title)
        assertEquals(obj.url,result.url)
    }

    @Test
    fun checkRemoteToLocalValues(){
        val obj = ObjectsProvider.provideFilmRemoteModel()

        val result = filmMapper.mapRemoteToLocal(obj)

        assertEquals(obj.created,result.created)
        assertEquals(obj.director,result.director)
        assertEquals(obj.edited,result.edited)
        assertEquals(obj.episodeId,result.episodeId)
        assertEquals(obj.openingCrawl,result.openingCrawl)
        assertEquals(obj.producer,result.producer)
        assertEquals(obj.releaseDate,result.releaseDate)
        assertEquals(obj.title,result.title)
        verify(urlExtractor).extract(obj.url)
        assertEquals("1",result.resourceId)
        assertEquals(obj.url,result.url)
    }

    @Test
    fun checkLocalToDomainValues(){
        val obj = ObjectsProvider.provideFilmLocalModel()

        val result = filmMapper.mapLocalToDomain(obj)

        assertEquals(0,result.characters.size)
        assertEquals(obj.created,result.created)
        assertEquals(obj.director,result.director)
        assertEquals(obj.edited,result.edited)
        assertEquals(obj.episodeId,result.episodeId)
        assertEquals(obj.openingCrawl,result.openingCrawl)
        assertEquals(obj.producer,result.producer)
        assertEquals(obj.releaseDate,result.releaseDate)
        assertEquals(obj.title,result.title)
        assertEquals(obj.url,result.url)

        assertEquals(0,result.characters.size)

        assertEquals(0,result.species.size)
    }
}