package com.amjad.starwars.common.utilities

import android.net.Uri
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


class UrlExtractor {


    fun extract(url: String): String {
        val uri = Uri.parse(url)
        return uri.lastPathSegment.toString()
    }

    fun extractList(urlList: List<String>): List<String> {
        return urlList.map {
            extract(
                it
            )
        }
    }

    fun extractPage(url: String): String {
        var yourParam: String? = null
        val uri = Uri.parse(url)
        try {
            yourParam = URLDecoder.decode(uri.getQueryParameter("page"), "UTF-8")
        } catch (exception: UnsupportedEncodingException) {
            exception.printStackTrace()
        }

        return yourParam.toString()

    }
}
