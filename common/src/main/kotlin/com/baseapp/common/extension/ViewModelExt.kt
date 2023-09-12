package com.baseapp.common.extension

import androidx.lifecycle.viewModelScope
import com.baseapp.common.base.BaseViewModel
import com.baseapp.model.common.ViewState
import com.baseapp.model.common.UIText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.net.ssl.HttpsURLConnection

fun <T> BaseViewModel.safeApiCall(callSuccess: suspend () -> T) {
    if (!isConnected.value) {
        setStatusViewModel(ViewState.ERROR(msg = UIText.DynamicString("No Internet Connection")))
    } else {
        setStatusViewModel(ViewState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                callSuccess()
                setStatusViewModel(ViewState.SUCCESS(true))
            } catch (t: Throwable) {
                if (!isConnected.value) {
                    setStatusViewModel(ViewState.ERROR(msg = UIText.DynamicString("Internet Not Connected")))
                } else {
                    val message = t.toThrowableMessage()
                    val code = t.toThrowableCode()
                    t.printStackTrace()
                    setStatusViewModel(ViewState.ERROR(msg = message, code = code, err = t))
                }
            }
        }
    }
}

fun BaseViewModel.safeApiCallIndependent(
    callSuccess: suspend () -> Unit,
    callError: suspend (Throwable) -> Unit = {}
) {
    if (!isConnected.value) {
        viewModelScope.launch(Dispatchers.Main) {
            callError(Throwable("No Internet Connection"))
        }
    } else {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                callSuccess()
            } catch (t: Throwable) {
                t.printStackTrace()
                callError(t)
            }
        }
    }
}

fun <T> BaseViewModel.safeApiCollect(
    state: MutableStateFlow<ViewState<T>>,
    callApi: suspend () -> Flow<ViewState<T>>,
    callSuccess: suspend (T) -> T = { it }
) {
    if (!isConnected.value) {
        setStatusViewModel(ViewState.ERROR(msg = UIText.DynamicString("No Internet Connection")))
    } else {
        viewModelScope.launch(Dispatchers.IO) {
            callApi.invoke().safeCollect {
                when (it) {
                    is ViewState.LOADING -> {
                        state.value = it
                        setStatusViewModel(ViewState.LOADING)
                    }

                    is ViewState.ERROR -> {
                        state.value = it
                        setStatusViewModel(
                            ViewState.ERROR(
                                msg = it.msg,
                                code = it.code,
                                err = it.err,
                                errResponse = it.errResponse,
                                data = true
                            )
                        )
                    }

                    is ViewState.EMPTY -> {
                        state.value = it
                        setStatusViewModel(it)
                    }

                    is ViewState.SUCCESS -> {
//                        state.value = it
                        setStatusViewModel(ViewState.SUCCESS(true))
                        state.value = ViewState.SUCCESS(callSuccess.invoke(it.data))
                    }
                }
            }
        }
    }
}

fun <T> BaseViewModel.safeApiCollectIndependent(
    state: MutableStateFlow<ViewState<T>>,
    callApi: suspend () -> Flow<ViewState<T>>,
    callSuccess: suspend (ViewState<T>) -> ViewState<T> = { it }
) {
    if (!isConnected.value) {
        state.value = ViewState.ERROR(
            code = HttpsURLConnection.HTTP_BAD_GATEWAY,
            msg = UIText.DynamicString("No Internet Connection"),
            data = when (state.value) {
                is ViewState.SUCCESS -> (state.value as ViewState.SUCCESS).data
                is ViewState.ERROR -> (state.value as ViewState.ERROR).data
                else -> null
            }
        )
    } else {
        viewModelScope.launch(Dispatchers.IO) {
            callApi.invoke().safeCollect {
                val emitData = callSuccess.invoke(it)
                state.emit(emitData)
            }
        }
    }
}