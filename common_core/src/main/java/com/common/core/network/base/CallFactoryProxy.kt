package com.common.core.network.base

import android.util.Log
import androidx.annotation.Nullable
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Request


/**
 *  Class:
 *  Other: apiservice中添加headers，然后判断这个参数来处理url的动态更换
 *  Create by jsji on  2021/7/1.
 */
abstract class CallFactoryProxy(val delegate: Call.Factory) : Call.Factory {
    companion object {
        const val NAME_BASE_URL_KEY = "BaseUrlName"
    }

    override fun newCall(request: Request): Call {

        val baseUrlName = request.header(NAME_BASE_URL_KEY);
        if (baseUrlName != null) {
            val newHttpUrl: HttpUrl? = getNewUrl(baseUrlName, request);
            if (newHttpUrl != null) {
                val newRequest: Request = request.newBuilder().url(newHttpUrl).build();
                return delegate.newCall(newRequest);
            } else {
                Log.w(
                    "CallFactoryProxy",
                    "getNewUrl() return null when baseUrlName==" + baseUrlName
                );
            }
        }
        return delegate.newCall(request);
    }

    @Nullable
    protected abstract fun getNewUrl(baseUrlName: String?, request: Request?): HttpUrl?
}