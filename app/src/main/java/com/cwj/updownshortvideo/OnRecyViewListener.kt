package com.cwj.updownshortvideo

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 监听接口
 */
interface OnRecyViewListener {
    /*初始化完成*/
    fun onInitComplete()

    /*释放的监听*/
    fun onPageRelease(isNext: Boolean, position: Int)

    /*选中的监听以及判断是否滑动到底部*/
    fun onPageSelected(position: Int, isBottom: Boolean)
}