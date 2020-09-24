package com.cwj.updownshortvideo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import cn.jzvd.JZUtils
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 重写播放器，方便控制。监听播放器状态。
 */

class JzvdStdTikTok : JzvdStd {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    override fun init(context: Context?) {
        super.init(context)
        bottomContainer.visibility = View.GONE
        currentTimeTextView.visibility = View.GONE
        totalTimeTextView.visibility = View.GONE //当前时间
        fullscreenButton.visibility = View.GONE //放大按钮
        topContainer.visibility = View.GONE
        progressBar.visibility = View.GONE //控制的
        loadingProgressBar.visibility = View.GONE //加载loaing
        bottomProgressBar.visibility = View.VISIBLE //最底部的进度
        posterImageView.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    override fun setUp(
        url: String?,
        title: String?,
        screen: Int,
        mediaInterfaceClass: Class<*>?
    ) {
        super.setUp(url, title, screen, mediaInterfaceClass)
    }

    //changeUiTo 真能能修改ui的方法
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        bottomContainer.visibility = View.GONE
        topContainer.visibility = View.GONE
        //        mDialogProgressBar.setVisibility(GONE);
    }

    override fun setAllControlsVisiblity(
        topCon: Int, bottomCon: Int, startBtn: Int, loadingPro: Int,
        posterImg: Int, bottomPro: Int, retryLayout: Int
    ) {
        topContainer.visibility = topCon
        bottomContainer.visibility = bottomCon
        startButton.visibility = startBtn
        loadingProgressBar.visibility = View.GONE
        posterImageView.visibility = posterImg
        bottomProgressBar.visibility = View.VISIBLE
        mRetryLayout.visibility = retryLayout
    }

    override fun dissmissControlView() {
        if (state != Jzvd.STATE_NORMAL && state != Jzvd.STATE_ERROR && state != Jzvd.STATE_AUTO_COMPLETE
        ) {
            post {
                bottomContainer.visibility = View.INVISIBLE
                topContainer.visibility = View.INVISIBLE
                startButton.visibility = View.INVISIBLE
                if (clarityPopWindow != null) {
                    clarityPopWindow.dismiss()
                }
                if (screen != Jzvd.SCREEN_TINY) {
                    bottomProgressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onClickUiToggle() {
        super.onClickUiToggle()
        Log.i(Jzvd.TAG, "click blank")
        startButton.performClick()
        bottomContainer.visibility = View.GONE
        topContainer.visibility = View.GONE
    }

    override fun onStateNormal() {
        super.onStateNormal()
    }

    override fun onStatePreparing() {
        super.onStatePreparing()
        Log.e("onStatePreparing", " 准备")
    }

    override fun onStatePlaying() {
        super.onStatePlaying()
        val times = duration
        Log.e("onStatePlaying", " $times")
    }

    override fun onStatePause() {
        super.onStatePause()
        Log.e("onStatePause:", "暂停")
    }

    override fun onStateError() {
        super.onStateError()
        Log.e("onStateError:", "错误")
    }

    //播放完成自动播放
    override fun onStateAutoComplete() {
        Toast.makeText(applicationContext, "onStateAutoComplete", Toast.LENGTH_SHORT).show()
    }

    override fun onCompletion() {
//        RxBus.INSTANCE.post(RXCmCWJ(121))
    }


    override fun updateStartImage() {
        if (state == Jzvd.STATE_PLAYING) {
            startButton.visibility = View.VISIBLE
            startButton.setImageResource(R.drawable.tiktok_play_tiktok)
            replayTextView.visibility = View.GONE
        } else if (state == Jzvd.STATE_ERROR) {
            startButton.visibility = View.INVISIBLE
            replayTextView.visibility = View.GONE
        } else if (state == Jzvd.STATE_AUTO_COMPLETE) {
            startButton.visibility = View.VISIBLE
            startButton.setImageResource(R.drawable.tiktok_play_tiktok)
            replayTextView.visibility = View.GONE
        } else {
            startButton.setImageResource(R.drawable.tiktok_play_tiktok)
            replayTextView.visibility = View.GONE
        }
    }
}
