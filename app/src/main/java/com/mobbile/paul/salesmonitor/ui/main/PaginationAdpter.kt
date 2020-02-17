package com.mobbile.paul.salesmonitor.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.mobbile.paul.salesmonitor.R

class PaginationAdpter: PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        var resId = 0

        when (position) {
            0 -> resId = R.id.page_one
            1 -> resId = R.id.page_two
            2 -> resId = R.id.page_three
        }
        return collection.findViewById(resId)
    }

    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}