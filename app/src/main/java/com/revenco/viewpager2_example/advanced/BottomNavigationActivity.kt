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
        const val INDEX_ONE = 0
        const val INDEX_TWO = 1
        const val INDEX_THREE = 2
        const val INDEX_Four = 3
        const val INDEX_FIVE = 4
    }

    private val fragmentCreator by lazy {
        mapOf(
            INDEX_ONE to BlankFragment(),
            INDEX_TWO to BlankFragment(),
            INDEX_THREE to BlankFragment(),
            INDEX_Four to BlankFragment(),
            INDEX_FIVE to BlankFragment()
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
            //bottomBar的多种切换动画

        }
    }

    private fun settingViewPager2() {
        bnVp2.adapter = BottomAdapter((this))
        //禁止滑动
        bnVp2.isUserInputEnabled=false
    }

    private fun withViewPagerAndBottom() {
        bottomBar.setOnNavigationItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.navigationOne -> {
                    bnVp2.setCurrentItem(INDEX_ONE, true)
                }
                R.id.navigationTwo -> {
                    bnVp2.setCurrentItem(INDEX_TWO, true)
                }
                R.id.navigationThree -> {
                    bnVp2.setCurrentItem(INDEX_THREE, true)
                }
                R.id.navigationFour -> {
                    bnVp2.setCurrentItem(INDEX_Four, true)
                }
                R.id.navigationFive -> {
                    bnVp2.setCurrentItem(INDEX_FIVE, true)
                }
            }
            true
        }
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