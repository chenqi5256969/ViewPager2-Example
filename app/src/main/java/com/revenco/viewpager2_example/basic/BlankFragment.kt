package com.revenco.viewpager2_example.basic

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.blankj.utilcode.util.LogUtils
import com.revenco.viewpager2_example.R
import com.revenco.viewpager2_example.vm.BlankViewModel
import kotlin.random.Random

class BlankFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutTopView = view.findViewById<FrameLayout>(R.id.layoutTopView)
        layoutTopView.setBackgroundColor(generateBackGroundColor.invoke())
    }

    private val generateBackGroundColor = {
        val red = Random.nextInt(0, 255)
        val green = Random.nextInt(0, 255)
        val blue = Random.nextInt(0, 255)
        Color.rgb(red, green, blue)
    }
}