package com.cherry.scrollablepanel.holder

import android.view.View
import com.cherry.scrollablepanel.data.ScrollablePanelCellInfo
import com.cherry.scrollablepanel.library.BaseContentViewHolder
import kotlinx.android.synthetic.main.rv_ad_rank_order_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AdRankOrderHolder
 * Author: Victor
 * Date: 2023/08/03 11:53
 * Description: 
 * -----------------------------------------------------------------
 */

class AdRankOrderHolder(itemView: View) : BaseContentViewHolder<ScrollablePanelCellInfo>(itemView) {

    override fun bindData(data: ScrollablePanelCellInfo?, column: Int, row: Int) {
        itemView.mTvAmount.text = data?.content
        if (data?.isAmount == true) {
            itemView.mTvAmountPrefix.visibility = View.VISIBLE
        } else {
            itemView.mTvAmountPrefix.visibility = View.INVISIBLE
        }

        itemView.mTvAmountPrefix.paint.isFakeBoldText = false
        itemView.mTvAmount.paint.isFakeBoldText = false
    }
}