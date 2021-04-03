package com.revenco.viewpager2_example.advanced

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.LogUtils
import com.revenco.viewpager2_example.R
import com.revenco.viewpager2_example.vm.BlankViewModel
import kotlin.random.Random

/**
 *  Copyright © 2021/4/4 Hugecore Information Technology (Guangzhou) Co.,Ltd. All rights reserved.
 *  author: chenqi
 */

class FourFragment : Fragment() {
    lateinit var blankViewModel: BlankViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutTopView = view.findViewById<FrameLayout>(R.id.layoutTopView)
        layoutTopView.setBackgroundColor(generateBackGroundColor.invoke())
        blankViewModel = ViewModelProvider(this).get(BlankViewModel::class.java)
        LogUtils.i("onViewCreated->four")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.i("onDestroy->four")
    }

    private val generateBackGroundColor = {
        val red = Random.nextInt(0, 255)
        val green = Random.nextInt(0, 255)
        val blue = Random.nextInt(0, 255)
        Color.rgb(red, green, blue)
    }
}