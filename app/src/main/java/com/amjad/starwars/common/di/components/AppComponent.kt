package com.amjad.starwars.common.di.components

import com.amjad.starwars.common.StarWarsChallengeApplication
import com.amjad.starwars.common.di.builders.ActivityBuilderModule
import com.amjad.starwars.common.di.builders.FragmentBuilderModule
import com.amjad.starwars.common.di.modules.AppModule
import com.amjad.starwars.common.di.modules.ViewModelModule
import com.amjad.starwars.data.di.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        DataModule::class

    ]
)
interface AppComponent : AndroidInjector<StarWarsChallengeApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: StarWarsChallengeApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(app: StarWarsChallengeApplication)
}