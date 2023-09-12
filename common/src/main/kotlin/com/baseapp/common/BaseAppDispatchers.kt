package com.baseapp.common

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseDefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseIODispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseMainDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class BaseMainImmediateDispatcher