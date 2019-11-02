package com.amjad.starwars.data.mappers

import android.provider.CalendarContract


interface Mapper<R, D> {

    fun mapFromEntity(entity: R): D

}
