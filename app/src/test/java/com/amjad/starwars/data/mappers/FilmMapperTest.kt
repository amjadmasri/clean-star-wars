package com.amjad.starwars.data.mappers

import com.amjad.starwars.ObjectsProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FilmMapperTest {


    private lateinit var filmMapper: FilmMapper

    @Before
    fun setup(){
        filmMapper =FilmMapper()
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