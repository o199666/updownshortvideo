package com.cwj.updownshortvideo

import android.app.Activity
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import cn.jzvd.JZUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cwj.updownshortvideo.MyApp.StaticParams.getProxy

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 适配器
 */
class VideoAdapter(var activity: Activity) :
    BaseQuickAdapter<VideoBean, BaseViewHolder>(R.layout.item_video) {
    override fun convert(holder: BaseViewHolder, item: VideoBean) {
        //圆形用户头像
        val requestOptions = RequestOptions.circleCropTransform()
        Glide.with(activity).load(item.user_image).apply(requestOptions)
            .into(holder.getView(R.id.user_iv))
        //用户名
        holder.setText(R.id.username_tv, item.user_name)
        //标题
        holder.setText(R.id.usertitle_tv, item.video_title)
        //缩略图
        Glide.with(activity).load(item.video_image)
            .into(holder?.getView<JzvdStdTikTok>(R.id.jz_video)!!.posterImageView)

        //声明 代理服务缓存
        val proxy = getProxy(activity)
        //这个缓存下一个
        if (holder?.layoutPosition!! + 1 < itemCount) {
            var item1 = getItem(holder?.layoutPosition!! + 1)
            //缓存下一个 10秒
            proxy!!.preLoad(item1!!.video_path, 10)
        }

        //缓存当前，播放当前
        var proxyUrl =proxy?.getProxyUrl(item.video_path).toString() //设置视



        setPlay(holder.getView(R.id.jz_video),proxyUrl)
    }


    fun setPlay(jzvdStdTikTok: JzvdStdTikTok, path: String) {
        Log.e("VideoAdapter", "${path}")
        //不保存播放进度
        Jzvd.SAVE_PROGRESS = false
        //取消播放时在非WIFIDialog提示
        Jzvd.WIFI_TIP_DIALOG_SHOWED = true
        // 清除某个URL进度
        //JZUtils.clearSavedProgress(activity, path)
        jzvdStdTikTok!!.setUp(path, "", JzvdStd.SCREEN_FULLSCREEN)

    }

}

 