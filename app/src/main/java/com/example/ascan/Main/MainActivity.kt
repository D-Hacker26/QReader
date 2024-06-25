package com.example.ascan.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.ascan.R
import com.example.ascan.db.database.ScanResultDataBase
import com.example.ascan.db.entities.ScanResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        for integrate our fragments here we have to make main pager adapter
//        after making page adaptor then then we have to integrate our pager adapter in main activity
        AddViewPagerAdapter()
        AddBottomNavigation()
        AddViewPagerListener()
        val scanResult=ScanResult(result = "DummyText", resultType = "TEXT", favourite = false, calendar = Calendar.getInstance())
        ScanResultDataBase.getAppDatabase(this)?.getScanDAO()?.insertScanResult(scanResult)
    }

    private fun AddViewPagerAdapter(){
//        here we have to connect the view pager adapter with the main pager adapter
    val viewpager=findViewById<ViewPager>(R.id.viewPager)
    viewpager.adapter= pageAdapter_Main(supportFragmentManager)
    }
    private fun AddBottomNavigation()
    {
        val viewpager=findViewById<ViewPager>(R.id.viewPager)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.Bottom_Navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
//                here we have successfully connected the fragments with the bottom nav view
//                here is a problem the page adapter are not connected so we swipe then the icon on the
//                btm nav view are not updated
                R.id.scan_menu ->viewpager.currentItem=0
                R.id.recent_scan ->viewpager.currentItem=1
                R.id.favourite ->viewpager.currentItem=2
            }
                return@setOnNavigationItemSelectedListener true
        }
    }
    private fun AddViewPagerListener()
    {
        val viewpager=findViewById<ViewPager>(R.id.viewPager)
        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.Bottom_Navigation)
        viewpager.addOnPageChangeListener(object:ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(
                position: Int)
            {
                fun onPageSelected(position: Int) {
                    when(position)
                    {
                        0->{
                            bottomNavigationView.selectedItemId= R.id.scan_menu
                        }
                        1->{
                            bottomNavigationView.selectedItemId= R.id.recent_scan
                        }
                        2->{
                            bottomNavigationView.selectedItemId= R.id.favourite
                        }
                    }
                }
            }
        })

    }
}