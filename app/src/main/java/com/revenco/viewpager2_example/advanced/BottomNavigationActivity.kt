package com.revenco.viewpager2_example.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.revenco.viewpager2_example.R
import com.revenco.viewpager2_example.basic.BlankFragment

class BottomNavigationActivity : AppCompatActivity() {
    lateinit var bottomBar: BottomNavigationView
    lateinit var bnVp2: ViewPager2

    companion object {
        const val INDEX_FIND = 0
        const val INDEX_EMPTY = 1
        const val INDEX_MINE = 2
    }

    private val fragmentCreator by lazy {
        mapOf(
            INDEX_FIND to BlankFragment(),
            INDEX_EMPTY to BlankFragment(),
            INDEX_MINE to BlankFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)
        bottomBar = findViewById(R.id.bottomBar)
        bnVp2 = findViewById(R.id.bnVp2)
        settingBottomNavigationView()
        settingViewPager2()

        withViewPagerAndBottom()
    }

    private fun settingBottomNavigationView() {
        bottomBar.apply {
            //当我们想要图标显示原始颜色的时候，需要加上这一句代码
            itemIconTintList = null
            //当然如果你想要图标显示你想要的颜色，可以参考xml配置: app:itemIconTint="@color/bottombar_icon_color"
        }
    }

    private fun settingViewPager2() {
        bnVp2.adapter = BottomAdapter((this))
    }

    private fun withViewPagerAndBottom() {
    }

    private inner class BottomAdapter constructor(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount() =
            fragmentCreator.size

        override fun createFragment(position: Int): Fragment {
            return fragmentCreator[position]
                ?: throw ArrayIndexOutOfBoundsException("Fragment not exist")

        }
    }
}