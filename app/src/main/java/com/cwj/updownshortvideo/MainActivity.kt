package com.cwj.updownshortvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import kotlinx.android.synthetic.main.activity_main.*

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 上下滑动 播放短视频。
 */

class MainActivity : AppCompatActivity() {
    lateinit var adapter: VideoAdapter
    private var mCurrentPosition = -1
    var videos = ArrayList<VideoBean>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }


    fun initView() {
        var recyViewLayoutManager = RecyViewLayoutManager(
            this, OrientationHelper.VERTICAL
        )
        recy.layoutManager = recyViewLayoutManager
        adapter = VideoAdapter(this)
        recy.adapter = adapter
        //预加载下一个
        adapter.setNewData(videos)
        //指定位置其他页面跳转过来。直接定位指定posion 这里不需要。
//        recy.scrollToPosition(postion);

        recyViewLayoutManager.setOnViewPagerListener(object : OnRecyViewListener {
            override fun onInitComplete() {
                //初始化 自动播放
                autoPlayVideo()

            }

            override fun onPageRelease(isNext: Boolean, position: Int) {
                //滑动时，释放上一个
                if (mCurrentPosition == position) {
                    Jzvd.releaseAllVideos()

                }
            }

            override fun onPageSelected(position: Int, isBottom: Boolean) {
                //滑动后的当前Item ，具体自行打印
                if (mCurrentPosition == position) {
                    return
                }
                if (isBottom) {
                    //是最底部，执行加载更多数据
                    loadData()
                }
                autoPlayVideo()
                mCurrentPosition = position
            }

        })

        ///监听item离开了屏幕
        recy.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                val jzvd: Jzvd = view.findViewById(R.id.jz_video)
                if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                    jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                ) {
                    if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.screen != Jzvd.SCREEN_FULLSCREEN) {
                        Jzvd.releaseAllVideos()
                    }
                }
            }

            override fun onChildViewAttachedToWindow(view: View) {
            }

        })
    }

    fun initData() {
        videos.add(
            VideoBean(
                1,
                "中韩夫妇与两宝",
                "https://p29-dy.byteimg.com/aweme/100x100/2f9480001ea8cc615d6a9.jpeg?from=401053103",
                "家有一老如有一宝，不分国界",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200fe90000bs854d21rirdcdon9fk0&ratio=720p&line=0",
                "https://p6-dy-ipv6.byteimg.com/img/tos-cn-p-0015/2263f31dfb304120a5fb5d6655b230c5_1594905185~tplv-dmt-logom:tos-cn-i-0813/65302cd29d2d4043a98de10a7723d33d.image?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                2,
                "央视新闻",
                "https://p6-dy-ipv6.byteimg.com/aweme/100x100/30e520009a01cad2d810e.jpeg?from=4010531038",
                "高三考生注意了！今年高考时间推迟一个月，为7月7日至7月8日",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200fe90000bs854d21rirdcdon9fk0&ratio=720p&line=0",
                "https://p29-dy.byteimg.com/obj/tos-cn-p-0015/f79a5fe204e24bbbbfc55a76a81f5c2c_1585626014?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                3,
                "查查和张张",
                "https://p6-dy-ipv6.byteimg.com/aweme/100x100/30e520009a01cad2d810e.jpeg?from=4010531038",
                "#情侣 #春节 就没有我解决不了的婆媳关系 嘿嘿@Singing哥 @抖音小助手",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200f670000bomln43d82dvbadk5a00&ratio=720p&line=0",
                "https://p29-dy.byteimg.com/obj/tos-cn-p-0015/f61dcb8127204a8cb7a322bf816c0b3e_1580030882?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                4,
                "大鹏（宇宙简史）",
                "https://p3-dy-ipv6.byteimg.com/aweme/100x100/1e1170002093b2ff1d0f7.jpeg?from=4010531038",
                "2018下半年所以天文奇观！还有流星雨哦！千万不要错过了",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200f4e0000bddrstnff778g23hs6mg&ratio=720p&line=0",
                "https://p6-dy-ipv6.byteimg.com/obj/9dfb0003c8c228b763eb?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                5,
                "胖爹带娃",
                "https://p3-dy-ipv6.byteimg.com/aweme/100x100/26ec600005035c9b87288.jpeg?from=4010531038",
                "带孩子出门玩耍一定要记得这一点！接力下去，别以为危险离我们很远@抖音小助手 #暑假安全",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200f4e0000bddrstnff778g23hs6mg&ratio=720p&line=0",
                "https://p9-dy.byteimg.com/obj/2c5c600050a7b42352869?from=2563711402_large"
            )
        )

    }
    //加载
    fun loadData() {
        videos.add(
            VideoBean(
                6,
                "遵义观察 ",
                "https://p9-dy.byteimg.com/aweme/100x100/2e1ce00021ee51a2aacdc.jpeg?from=4010531038",
                "老伴咱走！",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200fe10000bloib0nrri6bf7b1k4fg&ratio=720p&line=0",
                "https://p29-dy.byteimg.com/obj/tos-cn-p-0015/601c6d730167431184c5412e81cd32d1?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                7,
                "BTV养生堂 ",
                "https://p6-dy-ipv6.byteimg.com/aweme/100x100/3151700027839b153b924.jpeg?from=4010531038",
                "湿气过重，快收藏这个中医调理方！！",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200f730000bpebr0dqg5balrfhqlog&ratio=720p&line=0",
                "https://p3-dy-ipv6.byteimg.com/obj/tos-cn-p-0015/0e99f0aca9764e7da53be1096a3bd641_1583136211?from=2563711402_large"
            )
        )

        videos.add(
            VideoBean(
                8,
                "河南都市频道 ",
                "https://p9-dy.byteimg.com/aweme/100x100/312a8000720705660b806.jpeg?from=4010531038",
                "痛心！手扶梯绞断女童两根手指！带娃搭扶梯的一定要注意了！（上）！",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200f250000bgsu5vamac2seo2gp53g&ratio=720p&line=0",
                "https://p1-dy-ipv6.byteimg.com/obj/160b4000aa3f373bd14cd?from=2563711402_large"
            )
        )

        videos.add(
            VideoBean(
                9,
                "科学小妙招 ",
                "https://p26-dy.byteimg.com/aweme/100x100/312090000434b4dd10244.jpeg?from=4010531038",
                "不喜欢的衣服扔了可惜，这样改造一下非常酷#生活小妙招 #生活小技巧 #废物利用 @抖音小助手",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0200fe10000bq2bf7s9hq5lufbuoflg&ratio=720p&line=0",
                "https://p9-dy.byteimg.com/obj/tos-cn-p-0015/9a6784fcbf9b43849081d7a3388db08d_1585756125?from=2563711402_large"
            )
        )
        videos.add(
            VideoBean(
                10,
                "一起装修网 ",
                "https://p29-dy.byteimg.com/aweme/100x100/f77d000eae902034a2bf.jpeg?from=4010531038",
                "#装修 #黑幕重重 怎样选购浴室柜？揭露浴室柜增项（增项：镜子，水龙头，软管等）@胡 一刀",
                "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f9a0000belnavkqn5hfpb70b5kg&ratio=720p&line=0",
                "https://p29-dy.byteimg.com/obj/c8f200068c30b23f1024?from=2563711402_large"
            )
        )

    }


    /**
     * 滑动后自动播放。
     */
    private fun autoPlayVideo() {
        if (recy == null || recy.getChildAt(0) == null) {
            return
        }
        val player: JzvdStdTikTok = recy.getChildAt(0).findViewById(R.id.jz_video)
        if (player != null) {
            player.startVideoAfterPreloading()
            //播放开始，进行倒计时

        }
    }


    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()

    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()

    }
}