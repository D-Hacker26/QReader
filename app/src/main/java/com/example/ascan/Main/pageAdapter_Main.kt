package com.example.ascan.Main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.ascan.RecentsHistory.RecentFragments
import com.example.ascan.Scanner.Scanner_QRFragment

class pageAdapter_Main(var frag_mang:FragmentManager):FragmentStatePagerAdapter(frag_mang){
    override fun getCount(): Int {
        //      here we have to give in return number of fragments
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
//            it will handle our fragments instances
            0->Scanner_QRFragment.newInstance()
            1->RecentFragments.newInstance(RecentFragments.ScanResultListType.TOTAL_RESULT)
            2->RecentFragments.newInstance(RecentFragments.ScanResultListType.FAVOURITE_CLASS)
            else->Scanner_QRFragment.newInstance()
        }

    }

}