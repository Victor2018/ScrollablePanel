package com.cherry.scrollablepanel.library

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ScrollablePanel
 * Author: Victor
 * Date: 2023/4/6 14:16
 * Description: 
 * -----------------------------------------------------------------
 */

class ScrollablePanel : FrameLayout {
    var recyclerView: RecyclerView? = null
    var headerRecyclerView: RecyclerView? = null
    internal var panelLineAdapter: PanelLineAdapter? = null
    var panelAdapter: PanelAdapter? = null
    var firstItemView: FrameLayout? = null
    var mViewSpMask: View? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_scrollable_panel, this, true)
        recyclerView = findViewById<View>(R.id.recycler_content_list) as RecyclerView
        recyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        firstItemView = findViewById<View>(R.id.first_item) as FrameLayout
        headerRecyclerView = findViewById<View>(R.id.recycler_header_list) as RecyclerView
        headerRecyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        headerRecyclerView?.setHasFixedSize(true)
        if (panelAdapter != null) {
            panelLineAdapter = PanelLineAdapter(panelAdapter, recyclerView!!, headerRecyclerView!!,mViewSpMask)
            recyclerView?.adapter = panelLineAdapter
            setUpFirstItemView(panelAdapter!!)
        }
    }

    private fun setUpFirstItemView(panelAdapter: PanelAdapter) {
        val viewHolder: RecyclerView.ViewHolder? =
            panelAdapter.onCreateViewHolder(firstItemView, panelAdapter.getItemViewType(0, 0))
        panelAdapter.onBindViewHolder(viewHolder, 0, 0)
        firstItemView!!.addView(viewHolder?.itemView)
    }

    fun notifyDataSetChanged() {
        if (panelLineAdapter != null) {
            panelAdapter?.let { setUpFirstItemView(it) }
            panelLineAdapter!!.notifyDataChanged()
        }
    }

    /**
     * @param panelAdapter [PanelAdapter]
     */
    fun setScrollPanelAdapter(panelAdapter: PanelAdapter) {
        if (panelLineAdapter != null) {
            panelLineAdapter!!.setPanelAdapter(panelAdapter)
            panelLineAdapter!!.notifyDataSetChanged()
        } else {
            panelLineAdapter = PanelLineAdapter(panelAdapter, recyclerView!!, headerRecyclerView!!,mViewSpMask)
            recyclerView!!.adapter = panelLineAdapter
        }
        this.panelAdapter = panelAdapter
        setUpFirstItemView(panelAdapter)
    }

    /**
     * Adapter used to bind dataSet to cell View that are displayed within every row of [ScrollablePanel].
     */
    private class PanelLineItemAdapter(private var row: Int, panelAdapter: PanelAdapter?) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private val panelAdapter: PanelAdapter?

        init {
            this.panelAdapter = panelAdapter
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return panelAdapter?.onCreateViewHolder(parent, viewType)!!
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            panelAdapter?.onBindViewHolder(holder, row, position + 1)
        }

        override fun getItemViewType(position: Int): Int {
            return panelAdapter?.getItemViewType(row, position + 1) ?: 0
        }

        override fun getItemCount(): Int {
            return (panelAdapter?.getColumnCount() ?: 0)  - 1
        }

        fun setRow(row: Int) {
            this.row = row
        }
    }


    /**
     * Adapter used to bind dataSet to views that are displayed within a[ScrollablePanel].
     */
    internal class PanelLineAdapter(
        panelAdapter: PanelAdapter?,
        contentRV: RecyclerView,
        headerRecyclerView: RecyclerView,
        mViewSpMask: View?
    ) :
        RecyclerView.Adapter<PanelLineAdapter.ViewHolder?>() {
        private var panelAdapter: PanelAdapter?
        private val headerRecyclerView: RecyclerView
        private val contentRV: RecyclerView
        var mViewSpMask: View? = null
        private val observerList = HashSet<RecyclerView>()
        private var firstPos = -1
        private var firstOffset = -1

        init {
            this.panelAdapter = panelAdapter
            this.headerRecyclerView = headerRecyclerView
            this.contentRV = contentRV
            this.mViewSpMask = mViewSpMask
            initRecyclerView(headerRecyclerView)
            setUpHeaderRecyclerView()
        }

        fun setPanelAdapter(panelAdapter: PanelAdapter?) {
            this.panelAdapter = panelAdapter
            setUpHeaderRecyclerView()
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemCount(): Int {
            return (panelAdapter?.getRowCount() ?: 0) - 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val viewHolder = ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_content_row, parent, false)
            )
            initRecyclerView(viewHolder.recyclerView)
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var lineItemAdapter = holder.recyclerView.adapter as PanelLineItemAdapter?
            if (lineItemAdapter == null) {
                lineItemAdapter = PanelLineItemAdapter(position + 1, panelAdapter)
                holder.recyclerView.setAdapter(lineItemAdapter)
            } else {
                lineItemAdapter.setRow(position + 1)
                lineItemAdapter.notifyDataSetChanged()
            }
            if (holder.firstColumnItemVH == null) {
                val viewHolder: RecyclerView.ViewHolder? =
                    panelAdapter?.getItemViewType(position + 1, 0)?.let {
                        panelAdapter?.onCreateViewHolder(
                            holder.firstColumnItemView,
                            it
                        )
                    }
                holder.firstColumnItemVH = viewHolder
                panelAdapter?.onBindViewHolder(holder.firstColumnItemVH, position + 1, 0)
                holder.firstColumnItemView.addView(viewHolder?.itemView)
            } else {
                panelAdapter?.onBindViewHolder(holder.firstColumnItemVH, position + 1, 0)
            }
        }

        fun notifyDataChanged() {
            setUpHeaderRecyclerView()
            notifyDataSetChanged()
        }

        private fun setUpHeaderRecyclerView() {
            if (panelAdapter != null) {
                if (headerRecyclerView.adapter == null) {
                    val lineItemAdapter = PanelLineItemAdapter(0, panelAdapter)
                    headerRecyclerView.setAdapter(lineItemAdapter)
                } else {
                    headerRecyclerView.adapter!!.notifyDataSetChanged()
                }
            }
        }

        fun initRecyclerView(recyclerView: RecyclerView) {
            recyclerView.setHasFixedSize(true)
            val layoutManager: LinearLayoutManager? =
                recyclerView.layoutManager as LinearLayoutManager?
            if (layoutManager != null && firstPos!! > 0 && firstOffset > 0) {
                layoutManager.scrollToPositionWithOffset(firstPos + 1, firstOffset)
            }
            observerList.add(recyclerView)
            recyclerView.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> for (rv in observerList) {
                        rv.stopScroll()
                    }
                }
                false
            }
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (recyclerView.canScrollHorizontally(-1)) {
                        mViewSpMask?.visibility = View.VISIBLE
                    } else {
                        mViewSpMask?.visibility = View.GONE
                    }
                    val linearLayoutManager: LinearLayoutManager? =
                        recyclerView.layoutManager as LinearLayoutManager?
                    val firstPos: Int = linearLayoutManager?.findFirstVisibleItemPosition() ?: 0
                    val firstVisibleItem = linearLayoutManager?.getChildAt(0)
                    if (firstVisibleItem != null) {
                        val firstRight: Int =
                            linearLayoutManager.getDecoratedRight(firstVisibleItem)
                        for (rv in observerList) {
                            if (recyclerView !== rv) {
                                val layoutManager: LinearLayoutManager? =
                                    rv.layoutManager as LinearLayoutManager?
                                if (layoutManager != null) {
                                    this@PanelLineAdapter.firstPos = firstPos
                                    firstOffset = firstRight
                                    layoutManager.scrollToPositionWithOffset(
                                        firstPos + 1,
                                        firstRight
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

        internal class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var recyclerView: RecyclerView
            var firstColumnItemView: FrameLayout
            var firstColumnItemVH: RecyclerView.ViewHolder? = null

            init {
                recyclerView = view.findViewById<View>(R.id.recycler_line_list) as RecyclerView
                firstColumnItemView = view.findViewById<View>(R.id.first_column_item) as FrameLayout
                recyclerView.layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

}