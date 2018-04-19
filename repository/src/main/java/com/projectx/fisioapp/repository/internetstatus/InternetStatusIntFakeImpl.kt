package com.projectx.fisioapp.repository.internetstatus

import com.projectx.fisioapp.repository.utils.CodeClosure
import com.projectx.fisioapp.repository.utils.ErrorClosure

class InternetStatusIntFakeImpl: InternetStatusInteractor {
    override fun execute(success: CodeClosure, error: ErrorClosure) {
        success()
    }
}