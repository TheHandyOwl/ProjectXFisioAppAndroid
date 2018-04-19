package com.projectx.fisioapp.repository.internetstatus

import com.projectx.fisioapp.repository.utils.CodeClosure
import com.projectx.fisioapp.repository.utils.ErrorClosure

interface  InternetStatusInteractor {
    fun execute(success: CodeClosure, error: ErrorClosure)
}
