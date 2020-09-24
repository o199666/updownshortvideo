package com.cwj.updownshortvideo

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

/**
 *  author : ChenWenJie
 *  email  : 1181620038@qq.com
 *  date   : 2020/9/22
 *  desc   : 定义管理器，一item一屏 监听滑动状态
 */

class RecyViewLayoutManager : LinearLayoutManager {
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mOnRecycleViewListener: OnRecyViewListener? = null
    private var mRecyclerView: RecyclerView? = null
    private var mDrift //位移，用来判断移动方向
            = 0


    private val mChildAttachStateChangeListener: RecyclerView.OnChildAttachStateChangeListener =
        object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                if (mDrift >= 0) {
                    if (mOnRecycleViewListener != null) {
                        mOnRecycleViewListener!!.onPageRelease(true, getPosition(view))
                    }
                } else {
                    if (mOnRecycleViewListener != null) {
                        mOnRecycleViewListener!!.onPageRelease(false, getPosition(view))
                    }
                }
            }

            override fun onChildViewAttachedToWindow(view: View) {
                if (mOnRecycleViewListener != null && getChildCount() === 1) {
                    mOnRecycleViewListener!!.onInitComplete()
                }
            }
        }

    constructor(context: Context?, orientation: Int) : super(
        context,
        orientation,
        false
    ) {
        init()
    }

    constructor(
        context: Context?,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(context, orientation, reverseLayout) {
        init()
    }

    private fun init() {
        mPagerSnapHelper = PagerSnapHelper()
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        mPagerSnapHelper?.attachToRecyclerView(view)
        mRecyclerView = view
        mRecyclerView?.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        //
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     *
     * @param state
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle: View = mPagerSnapHelper!!.findSnapView(this)!!
                val positionIdle: Int = getPosition(viewIdle)
                if (mOnRecycleViewListener != null && getChildCount() === 1) {
                    mOnRecycleViewListener!!.onPageSelected(
                        positionIdle,
                        positionIdle == getItemCount() - 1
                    )
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> {
                val viewDrag: View? = mPagerSnapHelper!!.findSnapView(this)!!
                val positionDrag: Int = getPosition(viewDrag!!)
            }
            RecyclerView.SCROLL_STATE_SETTLING -> {
                val viewSettling: View = mPagerSnapHelper!!.findSnapView(this)!!
                val positionSettling: Int = getPosition(viewSettling)
            }
        }
    }

    /**
     * 监听竖直方向的相对偏移量
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    /**
     * 监听水平方向的相对偏移量
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    fun setOnViewPagerListener(listener: OnRecyViewListener?) {
        mOnRecycleViewListener = listener
    }

    companion object {
        private const val TAG = "ViewPagerLayoutManager"
    }
}
