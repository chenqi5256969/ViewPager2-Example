package com.revenco.viewpager2_example.basic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.revenco.viewpager2_example.R
import com.revenco.viewpager2_example.advanced.BottomNavigationActivity

private const val NUM_PAGES = 6


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toBottomNavigation()
        viewPager2 = findViewById<ViewPager2>(R.id.vp2).also { vp2 ->
            vp2.adapter = ScreenSlidePagerAdapter(this)
            vp2.setPageTransformer(DepthPageTransformer())
            vp2.offscreenPageLimit=2
        }
    }

    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager2.currentItem = viewPager2.currentItem - 1
        }
    }

    private inner class ScreenSlidePagerAdapter constructor(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        override fun getItemCount() = NUM_PAGES

        override fun createFragment(position: Int) = BlankFragment()
    }

    private fun toBottomNavigation() {
        val intent = Intent(this, BottomNavigationActivity::class.java)
        startActivity(intent)
    }
}