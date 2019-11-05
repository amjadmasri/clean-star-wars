package com.amjad.starwars.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amjad.starwars.data.local.dao.FilmDao
import com.amjad.starwars.data.utilities.LiveDataTestUtil
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.utilities.ObjectsProvider
import io.reactivex.Completable
import org.hamcrest.Matchers
import org.junit.Rule
import java.util.*


@RunWith(AndroidJUnit4::class)
class SimpleEntityReadWriteTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var filmDao: FilmDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java)
            .allowMainThreadQueries().build()
        filmDao = db.filmDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test fun getFilmWhenNoFilmInserted() {
        val result =LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(true,result==null)
    }

    @Test fun getFilmWhenFilmInserted() {
        val filmLocalDataModel = ObjectsProvider.provideFilmLocalDataModel("1")
        filmDao.insert(filmLocalDataModel)
            .subscribe()

        val result =LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(true,result!=null)
    }

    @Test fun getFilmWhenFilmListInserted() {
        val filmLocalDataModel = ObjectsProvider.provideFilmLocalDataModel("1")
        filmDao.insertList(arrayListOf(filmLocalDataModel))
            .subscribe()

        val result =LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(true,result!=null)
    }

    @Test fun getFilmWhenFilmListInsertedCheckData() {
        val filmLocalDataModel = ObjectsProvider.provideFilmLocalDataModel("1")
        filmDao.insertList(arrayListOf(filmLocalDataModel))
            .subscribe()

        val result =LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(filmLocalDataModel.episodeId,result.episodeId)
        Assert.assertEquals(filmLocalDataModel.created,result.created)
        Assert.assertEquals(filmLocalDataModel.director,result.director)
        Assert.assertEquals(filmLocalDataModel.edited,result.edited)
        Assert.assertEquals(filmLocalDataModel.openingCrawl,result.openingCrawl)

        Assert.assertEquals(filmLocalDataModel.producer,result.producer)
        Assert.assertEquals(filmLocalDataModel.releaseDate,result.releaseDate)
        Assert.assertEquals(filmLocalDataModel.title,result.title)
        Assert.assertEquals(filmLocalDataModel.url,result.url)

    }

    @Test fun getFilmWhenFilmListInsertedAndItemNotFound() {
        val filmLocalDataModel = ObjectsProvider.provideFilmLocalDataModel("2")
        filmDao.insertList(arrayListOf(filmLocalDataModel))
            .subscribe()

        val result =LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(true,result==null)
    }

    @Test fun insertTwoMoviesCheckReplaced(){

        val filmLocalDataModel = ObjectsProvider.provideFilmLocalDataModel("1")

        val filmLocalDataModel2 = FilmLocalDataModel(
            1,
            "created",
            "director",
            "edited",
            "crawl",
            "prod",
            "date",
            "title2",
            "1",
            "url",
            Date().time
        )

        filmDao.insert(filmLocalDataModel)
            .subscribe()

        filmDao.insert(filmLocalDataModel2)
            .subscribe()

        //check result from db directly
        val result = LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(result.title,filmLocalDataModel2.title)
    }

}