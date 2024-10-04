package com.anput.personalfinance.data.injection

import com.anput.personalfinance.data.repositories.BalanceReportsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun provideBalanceReportsRepository() : BalanceReportsRepository = BalanceReportsRepository()

}