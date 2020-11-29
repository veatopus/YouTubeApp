package kg.geektech.ruslan.youtubeapp.di

import kg.geektech.ruslan.youtubeapp.data.local.client.DatabaseClient
import kg.geektech.ruslan.youtubeapp.data.network.RetrofitClient
import kg.geektech.ruslan.youtubeapp.repository.YoutubeRepository
import kg.geektech.ruslan.youtubeapp.ui.playlist_info.PlaylistInfoViewModel
import kg.geektech.ruslan.youtubeapp.ui.playlists.PlaylistsViewModel
import kg.geektech.ruslan.youtubeapp.ui.video_info.VideoInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlaylistInfoViewModel(get()) }
    viewModel { VideoInfoViewModel(get()) }
}

var databaseModule = module {
    factory { DatabaseClient().provideDatabase(androidContext()) }
    factory { DatabaseClient().providePlaylistDao(get()) }
    factory { DatabaseClient().provideDetailPlaylistDao(get()) }

}

var networkModule = module {
    single { RetrofitClient() }
    single { RetrofitClient().instanceRetrofit() }
}

var repositoryModule = module {
    factory { YoutubeRepository(get(), get(), get()) }
}