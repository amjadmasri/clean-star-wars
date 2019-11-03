package com.amjad.starwars.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amjad.starwars.data.local.dao.FilmDao
import com.amjad.starwars.data.models.FilmLocalDataModel
import com.amjad.starwars.data.utilities.LiveDataTestUtil
import io.reactivex.Completable
import org.hamcrest.Matchers.instanceOf
import org.junit.*
import org.junit.Assert.assertThat
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FilmLocalSourceResponseTest {


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var filmDao: FilmDao
    private lateinit var db: AppDatabase

    private lateinit var filmLocalSource: FilmLocalSourceImp
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        )
            .allowMainThreadQueries().build()
        filmDao = db.filmDao()

        filmLocalSource=FilmLocalSourceImp(db)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertFilmResponseType() {
        val filmLocalDataModel = FilmLocalDataModel(
            1,
            "created",
            "director",
            "edited",
            "crawl",
            "prod",
            "date",
            "title",
            "1",
            "url"
        )
        assertThat(filmLocalSource.insertFilm(filmLocalDataModel),instanceOf(Completable::class.java))

        filmLocalSource.insertFilm(filmLocalDataModel)
            .subscribe()

        //check result from db directly
        val result = LiveDataTestUtil.getValue(filmDao.getFilmByResourceId("1"))

        Assert.assertEquals(filmLocalDataModel,result)
    }

    @Test
    fun testGetFilmResponseType() {
        assertThat(filmLocalSource.getFilmById("1"),instanceOf(LiveData::class.java))
    }

}