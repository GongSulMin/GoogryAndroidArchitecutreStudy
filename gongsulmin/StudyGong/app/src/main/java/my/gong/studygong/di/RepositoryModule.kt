package my.gong.studygong.di

import gong.team.data.source.upbit.UpbitDataSource
import gong.team.data.source.upbit.UpbitRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UpbitDataSource> { UpbitRepository(get()) }
}
