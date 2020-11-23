package kg.geektech.ruslan.youtubeapp

import android.app.Application
import kg.geektech.ruslan.youtubeapp.data.local.client.DatabaseClient
import kg.geektech.ruslan.youtubeapp.di.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class App : Application(){

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)

            modules(listOf(viewModelModule, databaseModule, networkModule, repositoryModule))
        }


    }
}
