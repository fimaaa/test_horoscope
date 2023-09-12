package com.baseapp.common.di

import com.baseapp.common.BaseDefaultDispatcher
import com.baseapp.common.BaseIODispatcher
import com.baseapp.common.BaseMainDispatcher
import com.baseapp.common.BaseMainImmediateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.components.ViewWithFragmentComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class, ActivityComponent::class, FragmentComponent::class, ViewModelComponent::class, ViewWithFragmentComponent::class)
object DispatchersModule {

    @Provides
    @BaseDefaultDispatcher
    internal fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @BaseIODispatcher
    internal fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @BaseMainDispatcher
    internal fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @BaseMainImmediateDispatcher
    internal fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main
}