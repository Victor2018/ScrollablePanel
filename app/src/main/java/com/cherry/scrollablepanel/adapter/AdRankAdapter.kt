package com.cherry.scrollablepanel.adapter

import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.cherry.scrollablepanel.R
import com.cherry.scrollablepanel.data.RoomBean
import com.cherry.scrollablepanel.data.ScrollablePanelCellInfo
import com.cherry.scrollablepanel.data.TitleBean
import com.cherry.scrollablepanel.holder.AdRankDateHolder
import com.cherry.scrollablepanel.holder.AdRankOrderHolder
import com.cherry.scrollablepanel.holder.AdRankRoomHolder
import com.cherry.scrollablepanel.holder.AdRankTitleHolder
import com.cherry.scrollablepanel.library.BaseScrollablePanelAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AdRankAdapter
 * Author: Victor
 * Date: 2023/08/03 11:46
 * Description: 
 * -----------------------------------------------------------------
 */

class AdRankAdapter(listener: AdapterView.OnItemClickListener) :
    BaseScrollablePanelAdapter<RoomBean, TitleBean, ScrollablePanelCellInfo, RecyclerView.ViewHolder>(listener) {

    override fun onCreateTitleVHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AdRankTitleHolder(inflate(R.layout.rv_ad_rank_title_cell, parent))
    }

    override fun onCreateDateVHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AdRankDateHolder(inflate(R.layout.rv_ad_rank_date_cell, parent))
    }

    override fun onCreateRoomVHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AdRankRoomHolder(inflate(R.layout.rv_ad_rank_room_cell, parent))
    }

    override fun onCreateOrderVHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return AdRankOrderHolder(inflate(R.layout.rv_ad_rank_order_cell, parent))
    }

    override fun onBindOrderVHolder(viewHolder: RecyclerView.ViewHolder, data: ScrollablePanelCellInfo?, column: Int, row: Int) {
        if (viewHolder is AdRankOrderHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data, column, row)
        }
    }

    override fun onBindRoomVHolder(viewHolder: RecyclerView.ViewHolder, data: RoomBean?, position: Int) {
        if (viewHolder is AdRankRoomHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data, position)
        }
    }

    override fun onBindDateVHolder(viewHolder: RecyclerView.ViewHolder, data: TitleBean?, position: Int) {
        if (viewHolder is AdRankDateHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data,position)
        }
    }

    override fun onBindTitleVHolder(viewHolder: RecyclerView.ViewHolder) {
    }

    fun setTitleOrderType(pos: Int,orderType: Int) {
        dateList.forEachIndexed { index, titleBean ->
            if (pos == index) {
                titleBean.orderType = orderType
            } else {
                titleBean.orderType = 0
            }
        }
    }
}