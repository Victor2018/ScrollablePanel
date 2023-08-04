package com.cherry.scrollablepanel.holder

import android.view.View
import com.cherry.scrollablepanel.R
import com.cherry.scrollablepanel.data.RoomBean
import com.cherry.scrollablepanel.library.BaseContentViewHolder
import kotlinx.android.synthetic.main.rv_ad_rank_room_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AdRankRoomHolder
 * Author: Victor
 * Date: 2023/08/03 11:53
 * Description: 
 * -----------------------------------------------------------------
 */

class AdRankRoomHolder(itemView: View) : BaseContentViewHolder<RoomBean>(itemView) {
    var row = -2

    fun bindData (data: RoomBean?, row: Int) {
        this.row = row

        itemView.mClAdRankRoomCell.setOnClickListener(this)
        itemView.mRoomName.text = data?.roomName

        if (row == 1) {
            itemView.mTvRank.setBackgroundResource(R.mipmap.ic_ad_rank1)
            itemView.mTvRank.text = ""
        } else if (row == 2) {
            itemView.mTvRank.setBackgroundResource(R.mipmap.ic_ad_rank2)
            itemView.mTvRank.text = ""
        } else if (row == 3) {
            itemView.mTvRank.setBackgroundResource(R.mipmap.ic_ad_rank3)
            itemView.mTvRank.text = ""
        } else {
            itemView.mTvRank.setBackgroundResource(R.drawable.shape_f0f7ff_radius_20)
            itemView.mTvRank.text = "$row"
        }

        itemView.mTvRank.paint.isFakeBoldText = false
        itemView.mRoomName.paint.isFakeBoldText = false
    }

    override fun onClick(view: View) {
        mOnItemClickListener?.onItemClick(null, view, row, ONITEM_CLICK)
    }
}