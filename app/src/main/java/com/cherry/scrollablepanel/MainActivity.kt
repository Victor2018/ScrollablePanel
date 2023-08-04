package com.cherry.scrollablepanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import com.cherry.scrollablepanel.adapter.AdRankAdapter
import com.cherry.scrollablepanel.data.AdRankInfo
import com.cherry.scrollablepanel.data.RoomBean
import com.cherry.scrollablepanel.data.ScrollablePanelCellInfo
import com.cherry.scrollablepanel.data.TitleBean
import com.cherry.scrollablepanel.util.AmountUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),OnItemClickListener {

    var mAdRankAdapter: AdRankAdapter? = null

    var adRankInfos: ArrayList<AdRankInfo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        initData()
    }

    fun initialize () {
        mAdRankAdapter = AdRankAdapter(this)
        mSpAdRank.mViewSpMask = mViewAdSpMask
    }

    fun initData () {
        initSpAdTitleData()

        adRankInfos = getAdRankList()
        showAdRankData(adRankInfos)
    }

    fun initSpAdTitleData() {
        //第一行
        val dateInfoList = java.util.ArrayList<TitleBean>()
        var titles = listOf("创意名称","创意ID","总成交数量","总业绩","总ROI")
        titles.forEachIndexed { index, s ->
            val titleBean = TitleBean()
            titleBean.title = s
            if (index == 2) {
                titleBean.orderType = 2
            }
            dateInfoList.add(titleBean)
        }

        mAdRankAdapter?.dateList = dateInfoList
    }

    fun getAdRankList(): ArrayList<AdRankInfo> {
        var adRankList = ArrayList<AdRankInfo>()
        for (i in 0 .. 50) {
            var item = AdRankInfo()
            item.title = "创意名称$i"
            item.totalPayOrderCount = 100 + 100 * i
            item.totalPayOrderAmount = 1000.0 + 100 * i
            item.userId = "user$i"
            item.creativeId = "18886688168$i"
            item.totalRoi = i * 1.0
            item.userName = "user-$i"
            adRankList.add(item)

        }
        return adRankList
    }

    fun showAdRankData(data: List<AdRankInfo>?) {

        //内容数据
        val ordersList = ArrayList<List<ScrollablePanelCellInfo>>()

        //第一列
        val roomInfoList = ArrayList<RoomBean>()

        data?.forEachIndexed { index, it ->
            val roomBean = RoomBean()
            roomBean.roomIdStr = it.userId
            roomBean.roomName = it.userName
            roomInfoList.add(roomBean)

            val orderInfoList: MutableList<ScrollablePanelCellInfo> =
                java.util.ArrayList<ScrollablePanelCellInfo>()
            //创意名称
            val orderInfo1 = ScrollablePanelCellInfo()
            orderInfo1.roomIdStr = it.userId
            orderInfo1.isAmount = false
            orderInfo1.content = it.title
            //创意ID
            val orderInfo2 = ScrollablePanelCellInfo()
            orderInfo2.roomIdStr = it.userId
            orderInfo2.isAmount = false
            orderInfo2.content = it.creativeId

            //总成交数量
            val orderInfo3 = ScrollablePanelCellInfo()
            orderInfo3.roomIdStr = it.userId
            orderInfo3.isAmount = false
            orderInfo3.content = AmountUtil.addCommaDotsForOrder(it.totalPayOrderCount?.toDouble() ?: 0.0,2,true,true)

            //总业绩
            val orderInfo4 = ScrollablePanelCellInfo()
            orderInfo4.roomIdStr = it.userId
            orderInfo4.isAmount = false
            orderInfo4.content = AmountUtil.addCommaDotsForOrder(it.totalPayOrderAmount,2,true,true)

            //总ROI
            val orderInfo5 = ScrollablePanelCellInfo()
            orderInfo5.roomIdStr = it.userId
            orderInfo5.isAmount = false
            orderInfo5.content = AmountUtil.addCommaDotsForOrder(it.totalRoi,2,true,true)

            orderInfoList.add(orderInfo1)
            orderInfoList.add(orderInfo2)
            orderInfoList.add(orderInfo3)
            orderInfoList.add(orderInfo4)
            orderInfoList.add(orderInfo5)

            ordersList.add(orderInfoList)
        }

        mAdRankAdapter?.roomList = roomInfoList
        mAdRankAdapter?.orderList = ordersList

        mSpAdRank.setScrollPanelAdapter(mAdRankAdapter!!)
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        when (v?.id) {
            R.id.mLlAdRankTitleCell -> {
                var titleData = mAdRankAdapter?.getDateItem(position)
                var orderType = titleData?.orderType
                if (orderType == 0) {
                    titleData?.orderType = 1
                } else if (orderType == 1) {
                    titleData?.orderType = 2
                } else if (orderType == 2) {
                    titleData?.orderType = 1
                }

                mAdRankAdapter?.setTitleOrderType(position - 1,titleData?.orderType ?: 0)
                when (position) {
                    3 -> {//创意名称
                        if (titleData?.orderType == 1) {
                            adRankInfos?.sortByDescending { it.totalPayOrderCount }
                        } else {
                            adRankInfos?.sortBy { it.totalPayOrderCount }
                        }
                        showAdRankData(adRankInfos)
                    }
                    4 -> {//总业绩
                        if (titleData?.orderType == 1) {
                            adRankInfos?.sortByDescending { it.totalPayOrderAmount }
                        } else {
                            adRankInfos?.sortBy { it.totalPayOrderAmount }
                        }
                        showAdRankData(adRankInfos)
                    }
                    5 -> {//总roi
                        if (titleData?.orderType == 1) {
                            adRankInfos?.sortByDescending { it.totalRoi }
                        } else {
                            adRankInfos?.sortBy { it.totalRoi }
                        }
                        showAdRankData(adRankInfos)
                    }
                }
            }
        }
    }
}