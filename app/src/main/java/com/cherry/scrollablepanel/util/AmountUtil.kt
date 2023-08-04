package com.cherry.scrollablepanel.util

import java.math.RoundingMode
import java.text.DecimalFormat

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AmountUtil
 * Author: Victor
 * Date: 2023/08/04 10:56
 * Description: 
 * -----------------------------------------------------------------
 */

object AmountUtil {
    fun addCommaDotsForOrder(value: Double,digit: Int,addUnit: Boolean,halfUp: Boolean): String {
        var inputValue = value
        var result = "0.00"
        var unitStr = ""
        if (addUnit) {
            if (value >= 100000000.0) {
                inputValue = value / 100000000.0
                unitStr = "\t亿"
            } else if (value >= 10000.0) {
                inputValue = value / 10000.0
                unitStr = "\t万"
            }
        }
        try {
            val myformat = DecimalFormat()
            if (!halfUp) {//比例接口已经四色五人了，这里直接处理显示
                myformat.roundingMode = RoundingMode.DOWN
            }
            var pattenSb = StringBuffer(",##0.")
            if (digit == 0) {
                pattenSb = StringBuffer(",##0")
            } else {
                for (i in 0 until digit) {
                    pattenSb.append("#")
                }
            }

            myformat.applyPattern(pattenSb.toString())//去除小数点后多余的0
            result = myformat.format(inputValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result + unitStr
    }
}