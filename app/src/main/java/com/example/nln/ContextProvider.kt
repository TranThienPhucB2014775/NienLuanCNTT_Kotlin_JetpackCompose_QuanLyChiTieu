package com.example.nln

import android.content.Context

object ContextProvider {
    private lateinit var context: Context

    fun setContext(appContext: Context) {
        context = appContext
    }

    fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
