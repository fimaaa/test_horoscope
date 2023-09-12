/*
 * Created by Yogi Dewansyah
 * URL: https://github.com/yodeput
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/28/22, 9:19 AM
 *
 */

package com.baseapp.repository.common

import com.baseapp.model.common.UIText
import com.baseapp.model.common.ViewState

interface Repository {
    val suspendOnExceptionError: ViewState.ERROR<Nothing>
        get() = ViewState.ERROR(msg = UIText.DynamicString("Terjadi kesalahan"), code = 502)
}