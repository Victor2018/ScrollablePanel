package com.cherry.scrollablepanel.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseScrollablePanelAdapter
 * Author: Victor
 * Date: 2023/08/03 11:07
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class BaseScrollablePanelAdapter<R,D,O,VH: RecyclerView.ViewHolder>(
    var listener: AdapterView.OnItemClickListener?) : PanelAdapter {

    abstract fun onCreateTitleVHolder(parent: ViewGroup?, viewType: Int): VH
    abstract fun onCreateDateVHolder(parent: ViewGroup?, viewType: Int): VH
    abstract fun onCreateRoomVHolder(parent: ViewGroup?, viewType: Int): VH
    abstract fun onCreateOrderVHolder(parent: ViewGroup?, viewType: Int): VH

    abstract fun onBindTitleVHolder(viewHolder: VH)
    abstract fun onBindDateVHolder(viewHolder: VH, data: D?, column: Int)
    abstract fun onBindRoomVHolder(viewHolder: VH, data: R?, row: Int)
    abstract fun onBindOrderVHolder(viewHolder: VH, data: O?, column: Int,row: Int)

    val TITLE_TYPE = 4
    val ROOM_TYPE = 0
    val DATE_TYPE = 1
    val CELL_TYPE = 2

    var roomList: ArrayList<R> = ArrayList()
    var dateList: List<D> = ArrayList()
    var orderList: List<List<O>> = ArrayList()

    var dataMap = LinkedHashMap<Int,R?>()//有序的map可以按照list选中顺序取出

    override fun getRowCount(): Int {
        return roomList.size + 1
    }

    override fun getColumnCount(): Int {
        return dateList.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            DATE_TYPE -> {
                return onCreateDateVHolder(parent, viewType)
            }
            ROOM_TYPE -> {
                return onCreateRoomVHolder(parent, viewType)
            }
            CELL_TYPE -> {
                return onCreateOrderVHolder(parent, viewType)
            }
            TITLE_TYPE -> {
                return onCreateTitleVHolder(parent, viewType)
            }
            else -> {
                return onCreateOrderVHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, row: Int, column: Int) {
        val viewType = getItemViewType(row, column)
        when (viewType) {
            DATE_TYPE -> {
                if (column > 0) {
                    onBindDateVHolder(holder as VH,getDateItem(column),column)
                }
            }
            ROOM_TYPE -> {
                if (row > 0) {
                    onBindRoomVHolder(holder as VH,getRoomItem(row),row)
                }
            }
            CELL_TYPE -> {
                onBindOrderVHolder(holder as VH,getOrderItem(column, row),column, row)
            }
            TITLE_TYPE -> {
                onBindTitleVHolder(holder as VH)
            }
        }
    }

    override fun getItemViewType(row: Int, column: Int): Int {
        if (column == 0 && row == 0) {
            return TITLE_TYPE
        }
        if (column == 0) {
            return ROOM_TYPE
        }
        return if (row == 0) {
            DATE_TYPE
        } else CELL_TYPE
    }

    fun inflate(layoutId: Int,parent: ViewGroup?): View {
        var inflater = LayoutInflater.from(parent?.context)
        return inflater.inflate(layoutId,parent, false)
    }

    fun getOrderItem(column: Int,row: Int): O? {
        //防止越界
        if (column < getColumnCount()) {
            val columnInfo = orderList[row - 1][column - 1]
            return columnInfo
        }
        return null
    }

    fun getRoomItem(row: Int): R? {
        //防止越界
        if (row < getRowCount()) {
            val roomInfo = roomList[row - 1]
            return roomInfo
        }
        return null
    }

    fun getDateItem(column: Int): D? {
        //防止越界
        if (column < getColumnCount()) {
            val dateInfo = dateList[column - 1]
            return dateInfo
        }
        return null
    }

    //////////////////////////// 选择处理 start /////////////////////////////////
    fun clearAllCheck() {
        dataMap.clear()
    }

    fun checkCell (data: R?) {
        if (isItemChecked(data)) {
            unCheck(data)
        } else {
            check(data)
        }
    }

    fun check(data: R?) {
        var key = getCheckKey(data)
        dataMap[key.hashCode()] = data
    }

    fun unCheck(data: R?) {
        var key = getCheckKey(data)
        dataMap.remove(key.hashCode())
    }

    fun isItemChecked (data: R?): Boolean {
        var key = getCheckKey(data).hashCode()
        return dataMap[key] != null
    }

    fun getCheckCount(): Int {
        return dataMap.size
    }

    fun getCheckDataList(): List<R> {
        var checkList = ArrayList<R>()
        dataMap.forEach {
            it.value?.let { it1 -> checkList.add(it1) }
        }

        return checkList
    }

    fun getCheckCell(key:String?): R? {
        return dataMap.get(key.hashCode())
    }

    open fun getCheckKey (data: R?): String {
        var key = data?.hashCode().toString()
        return key
    }
    //////////////////////////// 选择处理 end //////////////////////////////////
}