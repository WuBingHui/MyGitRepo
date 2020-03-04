package com.csnt.android_sport.game_detail.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class RepositoryViewPagerAdapter(private val tabList: MutableList<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(
        fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return tabList.size
    }

    override fun createFragment(position: Int): Fragment {
        return tabList[position]
    }

}