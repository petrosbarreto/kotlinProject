package br.com.roadmaps.kotlinproject

import android.content.Context
import com.github.rodlibs.persistencecookie.PersistentCookieStore
import com.squareup.okhttp.*
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

/**
 * Created by sidd on 16/06/18.
 */
class Network (var context: Context) {
    var URL: String = "http://imprensa-pcr.herokuapp.com/"
    var QUERY_LOGIN: String = "users/sign_in.json"
    var QUERY_GET_COMMENTS: String = "comments.json"


    var myCookie: PersistentCookieStore

    init {
        myCookie = PersistentCookieStore(context)
    }

    fun login(cb: HttpCallback) {
        val json: String = "{\"user\":{" +
                "\"email\":\""+ "crystian@roadmaps.com.br" +"\"," +
                "\"password\":\""+ "lklklklk" +"\"}}";


        if (myCookie == null) {
            myCookie = PersistentCookieStore(context);
        }
        val client = OkHttpClient()
        client.setConnectTimeout(20, TimeUnit.SECONDS)
        client.setWriteTimeout(20, TimeUnit.SECONDS)
        client.cookieHandler = CookieManager(myCookie, CookiePolicy.ACCEPT_ALL)

        val JSON = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
                .url(URL + QUERY_LOGIN)
                .post(body)
                .build()


        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                if (!call.isCanceled) {
                    (PersistentCookieStore(context)).removeAll();
                    cb.onFailure(null, e)
                }
            }
            override fun onResponse(response: Response) {
                val value = response.body().string()
                if (response.isSuccessful) {
                    cb.onSuccess(value)
                } else {
                    (PersistentCookieStore(context)).removeAll();
                    cb.onFailure(value, null)
                }
            }
        })
    }

    fun getComments(cb: HttpCallback){
        if (myCookie == null) {
            myCookie = PersistentCookieStore(context);
        }
        val client = OkHttpClient()
        client.setConnectTimeout(20, TimeUnit.SECONDS)
        client.setReadTimeout(30, TimeUnit.SECONDS)
        client.cookieHandler = CookieManager(myCookie, CookiePolicy.ACCEPT_ALL)

        val request = Request.Builder()
                .url(URL + QUERY_GET_COMMENTS)
                .get()
                .build()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(request: Request, e: IOException) {
                if (!call.isCanceled) {
                    cb.onFailure(null, e)
                }
            }
            override fun onResponse(response: Response) {
                val value = response.body().string()
                if (response.isSuccessful) {
                    cb.onSuccess(value)
                } else {
                    cb.onFailure(value, null)
                }
            }
        })
    }

    interface HttpCallback {
        fun onSuccess(response: String)
        fun onFailure(response: String?, throwable: Throwable?)
    }
}