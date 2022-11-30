package mn.turbo.marvel.common

import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import okhttp3.*

/**
 * callback interface to suspend function demo
 **/
suspend fun OkHttpClient.suspendNewCall(request: Request): Response {
    return suspendCoroutine { continuation ->
        newCall(request)
            .enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }
            })
    }
}
