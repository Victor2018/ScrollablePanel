package com.cherry.scrollablepanel.holder

import android.view.View
import com.cherry.scrollablepanel.library.BaseContentViewHolder
import kotlinx.android.synthetic.main.rv_ad_rank_title_cell.view.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AdRankTitleHolder
 * Author: Victor
 * Date: 2023/08/03 11:53
 * Description: 
 * -----------------------------------------------------------------
 */

class AdRankTitleHolder(itemView: View) : BaseContentViewHolder<String>(itemView) {
    fun bindData(data: String) {
        itemView.mTvTitle.text = data
    }
}