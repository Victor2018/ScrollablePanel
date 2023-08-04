package com.cherry.scrollablepanel.library

import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseContentViewHolder
 * Author: Victor
 * Date: 2023/08/04 10:21
 * Description: 
 * -----------------------------------------------------------------
 */

open class BaseContentViewHolder<T>: RecyclerView.ViewHolder, View.OnClickListener, View.OnLongClickListener {
    val TAG = javaClass.simpleName

    companion object {
        const val ONITEM_LONG_CLICK: Long = -1
        const val ONITEM_CLICK: Long = 0
    }

    var mOnItemClickListener: AdapterView.OnItemClickListener? = null

    constructor(itemView: View) : super(itemView) {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    override fun onClick(view: View) {
        mOnItemClickListener?.onItemClick(null, view, adapterPosition, ONITEM_CLICK)
    }

    override fun onLongClick(v: View): Boolean {
        mOnItemClickListener?.onItemClick(null, v, adapterPosition, ONITEM_LONG_CLICK)
        return false
    }

    open fun bindData (data: T?, column: Int,row: Int) {

    }
}