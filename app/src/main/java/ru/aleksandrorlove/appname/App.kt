package ru.aleksandrorlove.appname

import android.app.Application

class App : Application() {

    object Singletone {
        val instance = App()
    }
}