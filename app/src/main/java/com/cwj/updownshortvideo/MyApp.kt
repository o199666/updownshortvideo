package com.cwj.updownshortvideo

import android.app.Application
import android.content.Context
import com.cwj.updownshortvideo.SSlUtiles.TrustAllHostnameVerifier
import com.danikula.videocache.HttpProxyCacheServer
import javax.net.ssl.HttpsURLConnection

/**
 *  author : ChenWenJie
 * email  :1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   :
 */
class MyApp : Application() {
    //缓存代理服务
    private var proxy: HttpProxyCacheServer? = null
    override fun onCreate() {
        super.onCreate()

    }
    //获取缓存代理。
    object StaticParams{
        fun getProxy(context: Context): HttpProxyCacheServer? {
            var app: MyApp = context.applicationContext as MyApp
            return if (app.proxy == null) app.newProxy().also({ app.proxy = it }) else app.proxy
        }
//
    }



    private fun newProxy(): HttpProxyCacheServer? {
        return HttpProxyCacheServer.Builder(this)
            .maxCacheSize(1024 * 1024 * 1024)
            .maxCacheFilesCount(30)
            .build();
    }
}

 