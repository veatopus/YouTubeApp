package kg.geektech.ruslan.youtubeapp.di

import kg.geektech.ruslan.youtubeapp.data.local.client.DatabaseClient
import kg.geektech.ruslan.youtubeapp.data.network.RetrofitClient
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.PlaylistInfoViewModel
import kg.geektech.ruslan.youtubeapp.ui.playlists.PlaylistsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var mainModule = module {
    single { RetrofitClient() }
    single { RetrofitClient().instanceRetrofit() }

    single { DatabaseClient().provideDatabase(androidApplication()) }
    single { DatabaseClient().providePlaylist(get()) }
    factory { YoutubeRepository(get(), get()) }
}

var viewModelModule = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlaylistInfoViewModel(get()) }
}