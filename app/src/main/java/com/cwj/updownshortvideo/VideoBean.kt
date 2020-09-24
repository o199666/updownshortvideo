package com.cwj.updownshortvideo

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 视频实体类
 */
class VideoBean {
    // ID
    var id: Int = 0

    //用户名
    var user_name: String = ""

    //用户头像
    var user_image: String = ""

    //视频标题
    var video_title: String = ""

    //视频路径
    var video_path: String = ""

    //视频图片
    var video_image: String = ""

    constructor(
        id: Int,
        user_name: String,
        user_image: String,
        video_title: String,
        video_path: String,
        video_image: String
    ) {
        this.id = id
        this.user_name = user_name
        this.user_image = user_image
        this.video_title = video_title
        this.video_path = video_path
        this.video_image = video_image
    }
}



 