package com.hrithik.animation

import android.animation.Animator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import com.hrithik.animation.databinding.Activity3Binding
import kotlin.math.max


class Activity3 : AppCompatActivity() {

    private lateinit var binding: Activity3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Activity3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (savedInstanceState == null) {
                rootLayout.visibility = View.INVISIBLE
                val viewTreeObserver: ViewTreeObserver = rootLayout.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            circularRevealActivity()
                            rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }
            }
        }
    }

    private fun circularRevealActivity() {
        binding.apply {
            val cx: Int = rootLayout.right - getDips()
            val cy: Int = rootLayout.bottom - getDips()
            val finalRadius: Float = max(rootLayout.width, rootLayout.height).toFloat()

            val circularReveal =
                ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0f, finalRadius)
            circularReveal.duration = 1000
            rootLayout.visibility = View.VISIBLE
            circularReveal.start()
        }
    }

    private fun circularHideActivity() {
        binding.apply {
            val cx: Int = rootLayout.width - getDips()
            val cy: Int = rootLayout.bottom - getDips()
            val initialRadius: Float = max(rootLayout.width, rootLayout.height).toFloat()

            val circularReveal =
                ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, initialRadius, 0f)
            circularReveal.duration = 1000

            circularReveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    rootLayout.visibility = View.INVISIBLE
                    finish()
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationRepeat(p0: Animator?) {

                }
            })

            circularReveal.start()
        }
    }

    private fun getDips(): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60f, resources.displayMetrics)
            .toInt()
    }

    override fun onBackPressed() {
        circularHideActivity()
    }
}