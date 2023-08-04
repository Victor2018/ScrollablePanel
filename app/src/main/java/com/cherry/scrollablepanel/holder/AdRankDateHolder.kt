package com.cherry.scrollablepanel.holder

import android.view.View
import com.cherry.scrollablepanel.R
import com.cherry.scrollablepanel.data.TitleBean
import com.cherry.scrollablepanel.library.BaseContentViewHolder
import kotlinx.android.synthetic.main.rv_ad_rank_date_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AdRankDateHolder
 * Author: Victor
 * Date: 2023/08/03 11:53
 * Description: 
 * -----------------------------------------------------------------
 */

class AdRankDateHolder(itemView: View) : BaseContentViewHolder<TitleBean>(itemView) {
    var column = -2

    fun bindData (data: TitleBean?, column: Int) {
        this.column = column

        itemView.mLlAdRankTitleCell.setOnClickListener(this)
        itemView.mTvDate.text = data?.title

        if (data?.orderType == 0) {
            itemView.mIvOrderType.setImageResource(0)
        } else if (data?.orderType == 1) {
            itemView.mIvOrderType.setImageResource(R.mipmap.ic_down_sort)
        } else if (data?.orderType == 2) {
            itemView.mIvOrderType.setImageResource(R.mipmap.ic_up_sort)
        }

    }

    override fun onClick(view: View) {
        mOnItemClickListener?.onItemClick(null, view, column, ONITEM_CLICK)
    }
}